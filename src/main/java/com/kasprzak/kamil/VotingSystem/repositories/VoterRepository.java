package com.kasprzak.kamil.VotingSystem.repositories;

import com.kasprzak.kamil.VotingSystem.entities.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Long> {

    Optional<Voter> findByEmail(String email);

}
