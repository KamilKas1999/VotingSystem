package com.kasprzak.kamil.VotingSystem.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasprzak.kamil.VotingSystem.TestUtility;
import com.kasprzak.kamil.VotingSystem.dto.CreateElectionDto;
import com.kasprzak.kamil.VotingSystem.entities.Election;
import com.kasprzak.kamil.VotingSystem.repositories.ElectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ElectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ElectionRepository electionRepository;

    private ObjectMapper objectMapper = new ObjectMapper();;

    @BeforeEach
    void setup() {
        electionRepository.deleteAll();
    }

    @Test
    void shouldCreateElection() throws Exception {
        // given
        CreateElectionDto dto = CreateElectionDto.builder().name("name").options(List.of("option1")).build();

        // when
        String response = mockMvc.perform(post("/api/elections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        assertThat(response).contains("name");

        List<Election> elections = electionRepository.findAll();
        assertThat(elections).hasSize(1);
        assertThat(elections.getFirst().getName()).isEqualTo("name");
    }

    @Test
    void shouldGetAllElections() throws Exception {
        // given
        electionRepository.save(TestUtility.createElection("name1", "option1"));
        electionRepository.save(TestUtility.createElection("name2", "option2"));

        // when
        String response = mockMvc.perform(get("/api/elections"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        assertThat(response).contains("name1", "name2", "option1", "option2");
    }
}
