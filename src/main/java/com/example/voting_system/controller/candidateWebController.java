package com.example.voting_system.controller;


import com.example.voting_system.model.Candidate;
import com.example.voting_system.repo.CandidateRepository;
import com.example.voting_system.repo.ResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/candidates")
public class candidateWebController {
    @Autowired
    CandidateRepository candidateRepo;

    @Autowired
    ResultRepo resultRepo;

    @GetMapping("/add")
    public String add() {
        return "addCandidate";
    }

    @PostMapping("/addNewCandidate")
    public String addNewCandidate(RedirectAttributes redirectAttributes,
                                  @RequestParam("name") String name,
                                  @RequestParam("partyName") String partyName,
                                  @RequestParam("candidatePhoto") MultipartFile candidatePhoto,
                                  @RequestParam("symbolPhoto") MultipartFile symbolPhoto
    ) throws IOException {

        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setPartyName(partyName);
        candidate.setCandidatePhoto(candidatePhoto.getBytes());  // Convert to byte[]
        candidate.setSymbolPhoto(symbolPhoto.getBytes());        // Convert to byte[]

        candidateRepo.save(candidate);
        redirectAttributes.addFlashAttribute("successMessage", "Candidate successfully added!");
        return "redirect:/candidates/allCandidate";

    }

    @GetMapping("/allCandidate")
    public String getAllCandidates(Model model) {
        List<Candidate> candidates = candidateRepo.findAll();
        candidates.forEach(candidate -> {
            candidate.setCandidatePhotoBase64(Base64.getEncoder().encodeToString(candidate.getCandidatePhoto()));
            candidate.setSymbolPhotoBase64(Base64.getEncoder().encodeToString(candidate.getSymbolPhoto()));
        });

        model.addAttribute("candidates", candidates);
        return "allCandidates";
    }

    @GetMapping("/updateCandidate/{id}")
    public String updateCandidate(@PathVariable("id") Long id, Model model) {
        Candidate candidate = candidateRepo.findById(id).get();
        List<Candidate> candidates = candidateRepo.findAll();
        candidates.forEach(candidateData -> {
            candidateData.setCandidatePhotoBase64(Base64.getEncoder().encodeToString(candidateData.getCandidatePhoto()));
            candidateData.setSymbolPhotoBase64(Base64.getEncoder().encodeToString(candidateData.getSymbolPhoto()));
        });
        model.addAttribute("candidate", candidate);
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate/{id}")
    public String update(@PathVariable("id") Long id,
                         @RequestParam("name") String name,
                         @RequestParam("partyName") String partyName,
                         @RequestParam(value = "candidatePhoto", required = false) MultipartFile candidatePhoto,
                         @RequestParam(value = "symbolPhoto", required = false) MultipartFile symbolPhoto,
                         RedirectAttributes redirectAttributes, Model model) throws IOException {
        Candidate candidate = candidateRepo.findById(id).get();

        candidate.setName(name);
        candidate.setPartyName(partyName);
        if (candidatePhoto != null && !candidatePhoto.isEmpty()) {
            candidate.setCandidatePhoto(candidatePhoto.getBytes());
        }

        if (symbolPhoto != null && !symbolPhoto.isEmpty()) {
            candidate.setSymbolPhoto(symbolPhoto.getBytes());
        }

        candidateRepo.save(candidate);


        redirectAttributes.addFlashAttribute("successMessage", "Candidate successfully updated!");
        return "redirect:/candidates/allCandidate";
    }


    @PostMapping("/deleteCandidate/{id}")
    public String deleteCandidate(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Candidate candidate = candidateRepo.findById(id).orElse(null);

        System.out.println("---------------------------------------------------");

        assert candidate != null;
        resultRepo.deleteByCandidateId(candidate.getId());

        candidateRepo.delete(candidate);
        redirectAttributes.addFlashAttribute("successMessage", "Candidate successfully deleted!");
        return "redirect:/candidates/allCandidate";
    }
}
