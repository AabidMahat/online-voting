package com.example.voting_system.repo;

import com.example.voting_system.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ResultRepo extends JpaRepository<Result, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Result r WHERE r.candidate.id = :candidateId")
    void deleteByCandidateId(Long candidateId);
}
