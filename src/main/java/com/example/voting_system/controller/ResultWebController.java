package com.example.voting_system.controller;


import com.example.voting_system.model.Candidate;
import com.example.voting_system.model.Result;
import com.example.voting_system.repo.CandidateRepository;
import com.example.voting_system.repo.ResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/result")
public class ResultWebController {
    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    ResultRepo resultRepo;

    @PostMapping("/addCount")
    @ResponseBody
    public Map<String, Object> addCount(@RequestBody Map<String, Object> payload) {
        List<Result> results=resultRepo.findAll();
        Long candidateId = Long.valueOf((String) payload.get("candidateId"));
        for(Result result:results){
            if(result.getCandidate().getId().equals(candidateId)){
                result.setCount(result.getCount()+1);
                resultRepo.save(result);

            }

        }
       return Collections.singletonMap("results", results);
    }

    @ResponseBody
    @PostMapping("/addCandidates")
    public String addCandidates() {
        List<Candidate> candidate = candidateRepository.findAll();
        for (Candidate c : candidate) {
            Result result = new Result();

            result.setCandidate(c);

            resultRepo.save(result);
        }
        return "success";

    }

}
