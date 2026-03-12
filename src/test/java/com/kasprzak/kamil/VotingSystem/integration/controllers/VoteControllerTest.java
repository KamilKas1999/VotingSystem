package com.kasprzak.kamil.VotingSystem.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasprzak.kamil.VotingSystem.TestUtility;
import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;
import com.kasprzak.kamil.VotingSystem.entities.Election;
import com.kasprzak.kamil.VotingSystem.entities.Voter;
import com.kasprzak.kamil.VotingSystem.repositories.ElectionRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoteOptionRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoteRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoteRepository voteRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        voteRepository.deleteAll();
        voterRepository.deleteAll();
        electionRepository.deleteAll();
    }

    @Test
    void shouldVoteSuccessfully() throws Exception {


        var voter = voterRepository.save(TestUtility.createVoter("name", "email"));
        var election = electionRepository.save(TestUtility.createElection("name", "option1"));

        var dto = VoteRequestDto
                .builder()
                .voterId(voter.getId())
                .optionId(election.getOptions().getFirst().getId())
                .electionId(election.getId())
                .build();


        mockMvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAllVotesPaged() throws Exception {


        var voter = voterRepository.save(TestUtility.createVoter("name", "email"));
        var election = electionRepository.save(TestUtility.createElection("name", "option1"));
        var vote = voteRepository.save(TestUtility.createVote(voter, election, election.getOptions().getFirst()));

        mockMvc.perform(get("/api/votes")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].voterName").value(voter.getName()))
                .andExpect(jsonPath("$.content[0].optionName").value(election.getOptions().getFirst().getName()))
                .andExpect(jsonPath("$.content[0].electionName").value(election.getName()));
    }
}
