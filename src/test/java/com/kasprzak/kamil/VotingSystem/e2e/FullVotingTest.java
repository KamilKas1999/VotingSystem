package com.kasprzak.kamil.VotingSystem.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasprzak.kamil.VotingSystem.TestUtility;
import com.kasprzak.kamil.VotingSystem.dto.CreateElectionDto;
import com.kasprzak.kamil.VotingSystem.dto.CreateVoterDto;
import com.kasprzak.kamil.VotingSystem.dto.VoteRequestDto;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FullVotingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoteOptionRepository voteOptionRepository;

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
    @Transactional
    void fullVotingFlow() throws Exception {
        CreateVoterDto voterDto = TestUtility.createVoterDto("voterName", "email@email.com");
        String voterJson = objectMapper.writeValueAsString(voterDto);
        ResultActions voterResult = mockMvc.perform(post("/api/voters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voterJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        Long voterId = objectMapper.readTree(voterResult.andReturn()
                        .getResponse()
                        .getContentAsString())
                .get("id").asLong();

        CreateElectionDto electionDto = TestUtility.createElectionDto("electionName", "option1", "option2");

        String electionJson = objectMapper.writeValueAsString(electionDto);

        ResultActions electionResult = mockMvc.perform(post("/api/elections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(electionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        Long electionId = objectMapper.readTree(electionResult.andReturn()
                        .getResponse()
                        .getContentAsString())
                .get("id").asLong();

        Long optionId = electionRepository.findById(electionId).get().getOptions().getFirst().getId();

        VoteRequestDto voteDto = TestUtility.createVoteRequestDto(voterId,electionId, optionId);

        mockMvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voteDto)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/votes")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].voterName").value("voterName"))
                .andExpect(jsonPath("$.content[0].electionName").value("electionName"))
                .andExpect(jsonPath("$.content[0].optionName").value("option1"));
    }
}
