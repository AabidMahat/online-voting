package com.example.voting_system.controller;

import ch.qos.logback.core.model.Model;
import com.example.voting_system.model.Voter;
import com.example.voting_system.repo.VoterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/logIn")
public class LoginController {


    @Autowired
    VoterRepo voterRepo;

    private static final String ADMIN_SECRET = "adminSecret123";

    @GetMapping("/")
    public String login(Model model) {
        return "voter-login";
    }

    @ResponseBody
    @PostMapping("/login")
    public Object login(@RequestParam String phoneNumber,
                        @RequestParam String password) {

        Optional<Voter> voterData = voterRepo.findByPhoneNumber(phoneNumber);

        if (voterData.isPresent()) {
            Voter voter = voterData.get();

            if (voter.getPassword().equals(password)) {
                return voter;
            } else {
                return "Incorrect Phone Number or Password";
            }

        } else {
            return "Please Sign Up";
        }


    }

    @GetMapping("/adminLogin")
    public String adminLogin() {
        return "adminLogin";
    }



}
