package com.kasprzak.kamil.VotingSystem.repositories;

import com.kasprzak.kamil.VotingSystem.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByVoterIdAndElectionId(Long voterId, Long electionId);

}
