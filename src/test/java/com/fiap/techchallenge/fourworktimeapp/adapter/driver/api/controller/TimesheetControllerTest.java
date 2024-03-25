package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.controller;

import com.fiap.techchallenge.fourworktimeapp.application.dto.ClockEntryRequestDTO;
import com.fiap.techchallenge.fourworktimeapp.domain.usecase.TimesheetUseCase;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ContextConfiguration(classes = TestConfiguration.class)
@ExtendWith(SpringExtension.class)
public class TimesheetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TimesheetController controller;

    @Mock
    private TimesheetUseCase useCase;

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
        given(useCase.viewTimesheet(eq(employeeId))).willReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/v1/timesheet/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        // Assert
        verify(useCase, times(1)).viewTimesheet(any());
    }
}