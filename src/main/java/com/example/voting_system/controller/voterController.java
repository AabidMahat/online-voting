package com.example.voting_system.controller;

import com.example.voting_system.model.Candidate;
import com.example.voting_system.model.Election;
import com.example.voting_system.model.Voter;
import com.example.voting_system.repo.CandidateRepository;
import com.example.voting_system.repo.ElectionRepo;
import com.example.voting_system.repo.VoterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/voters")
public class voterController {

    @Autowired
    VoterRepo voterRepo;

    @Autowired
    CandidateRepository candidateRepo;

    @Autowired
    ElectionRepo electionRepo;

    @GetMapping("/cards")
    public String showCards(Model model){
        List<Voter> voter = voterRepo.findAll();
        model.addAttribute("voters", voter);
        return "card";
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        // Sample data
        List<String> parties = List.of("Party A", "Party B", "Party C");
        List<Integer> votesPerParty = List.of(100, 150, 80);

        int maleVoters = 120;
        int femaleVoters = 130;

        List<String> ageGroups = List.of("18-25", "26-35", "36-45", "46+");
        List<Integer> votesByAgeGroup = List.of(50, 90, 70, 40);

        int voted = 200; // Number of people who voted
        int notVoted = 50; // Number of people who did not vote

        model.addAttribute("parties", parties);
        model.addAttribute("votesPerParty", votesPerParty);
        model.addAttribute("maleVoters", maleVoters);
        model.addAttribute("femaleVoters", femaleVoters);
        model.addAttribute("ageGroups", ageGroups);
        model.addAttribute("votesByAgeGroup", votesByAgeGroup);
        model.addAttribute("voted", voted);
        model.addAttribute("notVoted", notVoted);

        return "chart";
    }

    @GetMapping("/giveVote")
    public String getAllCandidates(Model model) {
        String phoneNumber = "9201234567";
        Optional<Voter> voterData = voterRepo.findById(phoneNumber);
        List<Candidate> candidates = candidateRepo.findAll();
        candidates.forEach(candidate -> {
            candidate.setCandidatePhotoBase64(Base64.getEncoder().encodeToString(candidate.getCandidatePhoto()));
            candidate.setSymbolPhotoBase64(Base64.getEncoder().encodeToString(candidate.getSymbolPhoto()));
        });

        List<Election> elections = electionRepo.findAll();
        elections.forEach(election -> {
           election. setElectionBannerBase64(Base64.getEncoder().encodeToString(election.getElectionBanner()));
        });

        model.addAttribute("candidates", candidates);
        model.addAttribute("elections", elections);
        if(voterData.isPresent()) {
            Voter voter = voterData.get();
            model.addAttribute("isVoted", voter.isVoted());
        }
        return "Voting";
    }

    @ResponseBody
    @PostMapping("/make-vote")
    public Map<String, Object> giveVote(@RequestBody Map<String, Object> payload) {

        try {
            Long candidateId = Long.valueOf((String) payload.get("candidateId"));
            String phoneNumber = "9201234567";
            boolean isVoted = (boolean) payload.get("isVoted");



            Optional<Candidate> candidate = candidateRepo.findById(candidateId);
            Optional<Voter> voterData = voterRepo.findById(phoneNumber);

            if (candidate.isPresent() && voterData.isPresent()) {
                Voter voter = voterData.get();
                voter.setVoted(isVoted);
                voterRepo.save(voter);

                return Collections.singletonMap("voter", voter);
            } else {
                return Collections.singletonMap("error", "Candidate or Voter not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("error", e.getMessage());
        }
    }

}
