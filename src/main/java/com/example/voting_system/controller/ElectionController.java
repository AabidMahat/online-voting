package com.example.voting_system.controller;

import com.example.voting_system.model.Election;
import com.example.voting_system.repo.ElectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/election")
public class ElectionController {

    @Autowired
    ElectionRepo electionRepo;

    @GetMapping("/registerElection")
    public String registerElection(Model model) {
        return "election";
    }

    @PostMapping("/submitData")
    public String addElection(
            RedirectAttributes redirectAttributes,
            @RequestParam("name") String name,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam("electionPhoto") MultipartFile electionPhoto) throws IOException {

        // Remove all existing election records
        electionRepo.deleteAll();

        Election election = new Election();
        election.setName(name);
        election.setStartDate(startDate);
        election.setEndDate(endDate);
        election.setElectionBanner(electionPhoto.getBytes());

        electionRepo.save(election);

        redirectAttributes.addFlashAttribute("successMessage", "Candidate successfully added!");
        return "redirect:/voters/cards";
    }
}
