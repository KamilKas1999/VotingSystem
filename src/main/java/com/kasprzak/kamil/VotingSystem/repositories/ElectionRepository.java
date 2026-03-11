package com.kasprzak.kamil.VotingSystem.repositories;

import com.kasprzak.kamil.VotingSystem.entities.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}
