package com.kasprzak.kamil.VotingSystem.repositories;

import com.kasprzak.kamil.VotingSystem.entities.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByVoterIdAndElectionId(Long voterId, Long electionId);

    @EntityGraph(attributePaths = {"voter", "election", "option"})
    Page<Vote> findAll(Pageable pageable);
}
