package com.fiap.techchallenge.fourworktimeapp.adapter.driven.mail;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.TimesheetDailyEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.fiap.techchallenge.fourworktimeapp.util.EntityBuilder.buildDailyTimesheetEntry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ReportDispatcherImplTest {

    @Mock
    private JavaMailer emailSender;

    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    private ReportDispatcherImpl dispatcher;

    @BeforeEach
    public void setUp() {
        dispatcher = new ReportDispatcherImpl(emailSender, "lucasbz@gmail.com");
    }

    @Test
    void shouldHaveEveryDateEntryInTheReportBody() {
        // Arrange
        doNothing().when(emailSender).sendEmail(anyString(), anyString(), anyString(), bodyCaptor.capture());

        // Act
        dispatcher.sendLastTimesheet(List.of(
                buildDailyTimesheetEntry(2024, 03, 19, 8, 12),
                buildDailyTimesheetEntry(2024, 03, 20, 8, 10)
        ), "lucasbz@gmail.com");

        // Assert
        String fullEmailBody = bodyCaptor.getValue();
        System.out.println(fullEmailBody);
        assertThat(fullEmailBody).contains("Data");
        assertThat(fullEmailBody).contains("Horas Trabalhadas");
        assertThat(fullEmailBody).contains("2024-03-19");
        assertThat(fullEmailBody).contains("2024-03-20");
        assertThat(fullEmailBody).contains("08:00");
        assertThat(fullEmailBody).contains("06:00");
        assertThat(fullEmailBody).contains("Total de Horas Trabalhadas no mês: 14:00 horas");
    }

    @Test
    void shouldHaveZeroedWorkedHoursInTheReportBody() {
        // Arrange
        doNothing().when(emailSender).sendEmail(anyString(), anyString(), anyString(), bodyCaptor.capture());

        // Act
        dispatcher.sendLastTimesheet(List.of(TimesheetDailyEntry.builder()
                .date(LocalDate.now())
                .dailyClocks(Collections.emptyList())
                .build()), "lucasbz@gmail.com");

        // Assert
        String fullEmailBody = bodyCaptor.getValue();
        System.out.println(fullEmailBody);
        assertThat(fullEmailBody).contains("Data");
        assertThat(fullEmailBody).contains("Horas Trabalhadas");
        assertThat(fullEmailBody).contains("Total de Horas Trabalhadas no mês: 00:00 horas");
    }

}