package com.example.voting_system.controller;

import com.example.voting_system.services.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class SendEmail {

    @Autowired
    EmailSender emailSender;

    @PostMapping("/send-email")
    public Map<String,Object> sendMail(@RequestBody Map<String,Object> payload){

        String email = payload.get("email").toString();
        String name = payload.get("name").toString();
        boolean isVoted = (Boolean)  payload.get("isVoted");

        String emailSubject = isVoted ? "Thank you for voting!" : "Please remember to vote!";
        String emailMessage = isVoted ?
                "Dear " + name + ",\nThank you for participating in the election!" :
                "Dear " + name + ",\nPlease remember to cast your vote!";

        try {
            emailSender.sendEmail(email, name, emailSubject, emailMessage);
            return Collections.singletonMap("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("success", false);
        }
    }
}
