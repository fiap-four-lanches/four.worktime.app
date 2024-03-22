package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.controller;

import com.fiap.techchallenge.fourworktimeapp.application.dto.ClockEntryRequestDTO;
import com.fiap.techchallenge.fourworktimeapp.domain.usecase.ClockUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ContextConfiguration(classes = TestConfiguration.class)
@ExtendWith(SpringExtension.class)
public class ClockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ClockController controller;

    @Mock
    private ClockUseCase useCase;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void shouldReturnOkResponseWhenValidRequestIsGiven() throws Exception {
        // Arrange
        var employeeId = 1L;
        var clockedTime = LocalDateTime.now();
        boolean wasManuallyModified = false;
        var clockEntryRequest = new ClockEntryRequestDTO(employeeId, clockedTime, wasManuallyModified);
        var clockedEntry = clockEntryRequest.toEntry();
        given(useCase.clockInOrClockOut(eq(clockedEntry))).willReturn(clockedEntry.toClock());

        // Act & Assert
        mockMvc.perform(post("/v1/clock")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"employeeId\":1,\"clockedTime\":\"" + clockedTime + "\",\"wasManuallyModified\":false}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        // Assert
        verify(useCase, times(1)).clockInOrClockOut(any());
    }
}