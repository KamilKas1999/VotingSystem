package com.kasprzak.kamil.VotingSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "voters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean blocked = false;
}
