package com.example.microservicios.microserviciosexample.controller;

import com.example.microservicios.microserviciosexample.Service.*;
import com.example.microservicios.microserviciosexample.domain.EmailDTO;
import com.example.microservicios.microserviciosexample.domain.EmailFileDTO;
import com.example.microservicios.microserviciosexample.model.Applicant;
import com.example.microservicios.microserviciosexample.model.User;
import com.example.microservicios.microserviciosexample.model.Vacante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class MailController {
    @Autowired
    private IEmailService emailService;
    @Autowired
    private IApplicanService apliServ;
    @Autowired
    private IVacannteService vacaServ;


    @PostMapping("/sendMessage")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody EmailDTO emailDTO){
        System.out.println("Mensaje Recibido" + emailDTO);
        emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());
        Map<String, String> response= new HashMap<>();
        response.put("estado", "Enviado");
        return ResponseEntity.ok(response);

    }

    //para nueva vacante
    @PostMapping("/sendMessageApplicants/")
    public EmailDTO sendMessageApplicant(@RequestBody Vacante vacante) {
        List<Applicant> applicantList = apliServ.getApplicants();
        Vacante vacante1 = new Vacante();
        vacante1.setNombreVacante(vacante.getNombreVacante());
        vacante1.setDescripcion(vacante.getDescripcion());
        vacante1.setRequisitos(vacante.getRequisitos());
        vacante1.setCantidadDisponible(vacante.getCantidadDisponible());

        EmailDTO emailDTO = new EmailDTO();
        ArrayList<String> correoList = new ArrayList<>();
        for (Applicant applicant : applicantList) {
            String correo = applicant.getEmail();
            correoList.add(correo);
        }
        ArrayList<String> nombreList = new ArrayList<>();
        for(Applicant applicant : applicantList){
            String nombre= applicant.getFirstName();
            nombreList.add(nombre);
        }

        emailDTO.setToUser(correoList.toArray(new String[0]));
        emailDTO.setSubject("¡Ténemos nuevas vacantes!");
        emailDTO.setMessage("Hola, tenemos una nueva vacante en las que puedes participar: " + vacante1.getNombreVacante() + ".\n\n" +
                "Haz click aqui para postular: https://front-salty-rrhh.vercel.app/ " + ".\n\n" +
                "Ten un excelente y Bendecido Día, Saludos");
        //System.out.println("Mensaje Recibido: " + emailDTO);
        emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");
        return emailDTO;
    }

    //enviar mensaje al reclutador RRHH
    @PostMapping("/{idApplicant}/sendMessageApplicants/{id}")
    public EmailDTO sendMessageRecruiter( @PathVariable Long idApplicant,  @PathVariable Long id){
        List<String> userList= new ArrayList<>();
        Applicant applicant= apliServ.findApp(idApplicant);
        Vacante vacante= vacaServ.findVacante(id);
        userList.add("tuyerosaltysnack@gmail.com");

       EmailDTO emailDTO= new EmailDTO();
       emailDTO.setToUser(userList.toArray(new String[0]));
       emailDTO.setSubject("Notificacion de Aplicante");
       emailDTO.setMessage("Hola, el/la Sr(a) "+ applicant.getFirstName()+ " "+ applicant.getLastName()+ " "+ "en la vacante: "+ vacante.getNombreVacante() + ".\n\n" +
               "Haz click aqui para  revisar el CV: https://front-salty-rrhh.vercel.app/ " +".\n\n" +"Saludos"     );

       //System.out.println("Mensaje Recibido"+  emailDTO);
       emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());
       Map<String, String>  response = new HashMap<>();
       response.put("estado", "Enviado");
       return  emailDTO;

    }
    @PostMapping("/sendMessageApplicants/{idApplicant}/{id}")
    public EmailDTO confirmacionApplicant(@PathVariable Long idApplicant, @PathVariable Long id) {
        System.out.println("Entering confirmacionApplicant method");
        List<String> userList = new ArrayList<>();
        Applicant applicant = apliServ.findApp(idApplicant);
        Vacante vacante = vacaServ.findVacante(id);

        if (applicant == null) {
            System.out.println("Applicant not found with ID: " + idApplicant);
            throw new RuntimeException("Applicant not found");
        }

        if (vacante == null) {
            System.out.println("Vacante not found with ID: " + id);
            throw new RuntimeException("Vacante not found");
        }

        userList.add(applicant.getEmail());
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setToUser(userList.toArray(new String[0]));
        emailDTO.setSubject("Te has postulado correctamente");
        emailDTO.setMessage("Hola, " + applicant.getFirstName() + " te has posutulado correctamente como: " + vacante.getNombreVacante() + ".\n\n" +
                "Estaremos revisando tu perfil, en caso de ajustarse a nuestra busqueda, nos comunicaremos contigo." + ".\n\n" + "Muchos Exitos");

        //System.out.println("Mensaje Recibido: " + emailDTO);
        emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());

        //System.out.println("Email sent successfully to: " + userList);
        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return emailDTO;
    }

    @PostMapping("/sendMessageFile")
    public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO){

        try{
            String fileName= emailFileDTO.getFile().getOriginalFilename();
            Path path= Paths.get("src/main/resources/files"+ fileName);
            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            File file= path.toFile();
            emailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), emailFileDTO.getMessage(), file);
            Map<String,String> response= new HashMap<>();
            response.put("estado", "Enviado");
            response.put("archivo", fileName);
            return ResponseEntity.ok(response);

        }catch (Exception e){
        throw new RuntimeException("Error al enviar el  email con archivo"+ e.getMessage());
        }
    }










}
