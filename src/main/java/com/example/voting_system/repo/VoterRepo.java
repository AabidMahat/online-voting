package com.example.voting_system.repo;

import com.example.voting_system.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepo extends JpaRepository<Voter, String> {

    Optional<Voter> findByPhoneNumber(String phoneNumber);

}
