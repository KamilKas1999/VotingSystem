package com.kasprzak.kamil.VotingSystem.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "election_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteOption extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;
}
