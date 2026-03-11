package com.kasprzak.kamil.VotingSystem.repositories;

import com.kasprzak.kamil.VotingSystem.entities.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteOptionRepository extends JpaRepository<VoteOption, Long> {

    List<VoteOption> findByElectionId(Long electionId);

}
