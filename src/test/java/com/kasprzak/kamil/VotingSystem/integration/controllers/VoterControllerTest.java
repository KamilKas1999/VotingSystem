package com.kasprzak.kamil.VotingSystem.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasprzak.kamil.VotingSystem.TestUtility;
import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;
import com.kasprzak.kamil.VotingSystem.entities.Election;
import com.kasprzak.kamil.VotingSystem.entities.VoteOption;
import com.kasprzak.kamil.VotingSystem.entities.Voter;
import com.kasprzak.kamil.VotingSystem.repositories.ElectionRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoteOptionRepository;
import com.kasprzak.kamil.VotingSystem.repositories.VoterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VoterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VoterRepository voterRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        voterRepository.deleteAll();
    }

    @Test
    void shouldCreateVoter() throws Exception {
        // given
        var dto = CreateVoterDto.builder().email("email@email.com").name("name").build();

        // when + then
        mockMvc.perform(post("/api/voters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldGetAllVoters() throws Exception {
        // given
        Voter voter1 = voterRepository.save(TestUtility.createVoter("name1", "email1"));
        Voter voter2 = voterRepository.save(TestUtility.createVoter("name2", "email2"));

        // when + then
        mockMvc.perform(get("/api/voters")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("name1"))
                .andExpect(jsonPath("$.content[1].name").value("name2"));
    }

    @Test
    void shouldBlockVoter() throws Exception {
        // given
        Voter voter = voterRepository.save(TestUtility.createVoter("name1", "email1"));

        // when
        mockMvc.perform(post("/api/voters/{id}/block", voter.getId()))
                .andExpect(status().isOk());

        // then
        Voter blockedVoter = voterRepository.findById(voter.getId()).get();
        assert(blockedVoter.isBlocked());
    }

    @Test
    void shouldUnblockVoter() throws Exception {
        // given
        Voter voter = voterRepository.save(TestUtility.createVoter("name1", "email1"));
        voter.setBlocked(true);
        voterRepository.save(voter);

        // when
        mockMvc.perform(post("/api/voters/{id}/unblock", voter.getId()))
                .andExpect(status().isOk());

        // then
        Voter unblockedVoter = voterRepository.findById(voter.getId()).get();
        assert(!unblockedVoter.isBlocked());
    }
}
