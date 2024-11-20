package com.example.voting_system.repo;

import com.example.voting_system.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepo extends JpaRepository<Election, Long> {
}
