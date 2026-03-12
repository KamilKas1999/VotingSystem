package com.kasprzak.kamil.VotingSystem;

import com.kasprzak.kamil.VotingSystem.dto.CreateElectionDto;
import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;
import com.kasprzak.kamil.VotingSystem.entities.Election;
import com.kasprzak.kamil.VotingSystem.entities.Vote;
import com.kasprzak.kamil.VotingSystem.entities.VoteOption;
import com.kasprzak.kamil.VotingSystem.entities.Voter;

import java.util.List;

public class TestUtility {

    public static Voter createVoter(String name, String email){
        return Voter.builder().name(name).email(email).blocked(false).build();
    }

    public static Election createElection(String name, String voteOptions){
        var election = Election.builder().name(name).build();
        VoteOption option = TestUtility.createVoteOption(voteOptions);
        option.setElection(election);
        election.setOptions(List.of(option));
        return election;
    }

    public static Vote createVote(Voter voter, Election election, VoteOption voteOption){
        return Vote.builder().voter(voter).election(election).option(voteOption).build();
    }

    public static VoteOption createVoteOption(String name){
        return VoteOption.builder().name(name).build();
    }

    public static CreateVoterDto createVoterDto(String name, String email){
        return CreateVoterDto.builder().name(name).email(email).build();
    }

    public static CreateElectionDto createElectionDto(String name, String option1, String option2){
        return CreateElectionDto.builder().name(name).options(List.of(option1, option2)).build();
    }

    public static VoteRequestDto createVoteRequestDto(Long voterId, Long electionId, Long optionId){
        return VoteRequestDto.builder().voterId(voterId).electionId(electionId).optionId(optionId).build();
    }
}
