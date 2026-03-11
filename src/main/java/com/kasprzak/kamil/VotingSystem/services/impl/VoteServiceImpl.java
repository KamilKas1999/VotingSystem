package com.kasprzak.kamil.VotingSystem.services.impl;

import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;
import com.kasprzak.kamil.VotingSystem.entities.Election;
import com.kasprzak.kamil.VotingSystem.entities.Vote;
import com.kasprzak.kamil.VotingSystem.entities.VoteOption;
import com.kasprzak.kamil.VotingSystem.entities.Voter;
import com.kasprzak.kamil.VotingSystem.exceptions.*;
import com.kasprzak.kamil.VotingSystem.repositories.ElectionRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoteOptionRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoteRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoterRepository;
import com.kasprzak.kamil.VotingSystem.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final VoterRepository voterRepository;
    private final ElectionRepository electionRepository;
    private final VoteOptionRepository voteOptionRepository;

    @Override
    @Transactional
    public void vote(VoteRequestDto dto) {

        var voter = getVoter(dto.voterId());

        var election = getElection(dto.electionId());

        var voteOption = getCandidate(dto.optionId());

        validateOption(voteOption, election);

        validateIfUserAlreadyVote(dto.voterId(), dto.electionId());

        var vote = Vote
                .builder()
                .voter(voter)
                .election(election)
                .option(voteOption)
                .build();

        voteRepository.save(vote);
    }

    private void validateIfUserAlreadyVote(Long voterId, Long electionId) {
        if (voteRepository.existsByVoterIdAndElectionId(voterId, electionId)) {
            throw new UserAlreadyVotedException("Voter already voted in this election");
        }
    }

    private static void validateOption(VoteOption voteOption, Election election) {
        if (!voteOption.getElection().getId().equals(election.getId())) {
            throw new OptionNotBelongsToElectionException("Candidate does not belong to this election");
        }
    }

    private VoteOption getCandidate(Long id) {
        return voteOptionRepository.findById(id)
                .orElseThrow(() -> new VoteOptionNotFoundException("Option not found"));
    }

    private Election getElection(Long id) {
        return electionRepository.findById(id)
                .orElseThrow(() -> new ElectionNotFoundException("Election not found"));
    }

    private Voter getVoter(Long id) {
        var voter = voterRepository.findById(id)
                .orElseThrow(() -> new VoterNotFoundException("Voter not found"));

        if (voter.isBlocked()) {
            throw new VoterIsBlockedException("Voter is blocked");
        }
        return voter;
    }
}
