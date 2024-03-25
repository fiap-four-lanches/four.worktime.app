package com.fiap.techchallenge.fourworktimeapp.adapter.driven.mail;

import com.fiap.techchallenge.fourworktimeapp.domain.entity.ClockType;
import com.fiap.techchallenge.fourworktimeapp.domain.entity.TimesheetDailyEntry;
import com.fiap.techchallenge.fourworktimeapp.domain.repository.ReportDispatcher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.String.format;

@Service
public class ReportDispatcherImpl implements ReportDispatcher {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private JavaMailer emailSender;
    private String emailFrom;

    public ReportDispatcherImpl(@Autowired JavaMailer emailSender, @Value("${report.mailer.from}") String emailFrom) {
        this.emailSender = emailSender;
        this.emailFrom = emailFrom;
    }

    @Override
    public void sendLastTimesheet(List<TimesheetDailyEntry> reportEntries, String requesterEmail) {
        StringBuilder reportBody = new StringBuilder();
        reportBody.append("<html>");
        reportBody.append("\n<body style=\"font-family: Arial\">");
        reportBody.append("\n<table>");
        reportBody.append("\n<tr>");
        reportBody.append("\n<th>Data</th>");
        reportBody.append("\n<th>Horas Trabalhadas</th>");
        reportBody.append("\n<th>Registros</th>");
        reportBody.append("\n</tr>");

        reportEntries.stream().forEach(timesheetDailyEntry -> {
            reportBody.append("\n<tr>");
            reportBody.append(format("\n<td><b>%s</b></td>", formatter.format(timesheetDailyEntry.getDate())));
            reportBody.append(format("\n<td>%s</td>", formatHoursMinutes(timesheetDailyEntry.getWorkedHoursInMinutes())));
            reportBody.append("\n</tr>");
            timesheetDailyEntry.getDailyClocks().stream().forEach(clock -> {
                String clockTypeText = clock.getClockType() == ClockType.IN ? "ENTRADA" : "SAIDA";
                reportBody.append("\n<tr>");
                reportBody.append("\n<td>&nbsp;</td><td>&nbsp;</td>");
                reportBody.append(format("\n<td>%02d:%02d - %s</td>", clock.getClockedTime().getHour(), clock.getClockedTime().getMinute(), clockTypeText));
                reportBody.append("\n</tr>");
            });
        });

        reportBody.append("\n</table>");
        reportBody.append("\n</br>");
        reportBody.append(format("\n<b>Total de Horas Trabalhadas no mês: %s horas</b>", formatHoursMinutes(reportEntries.stream().map(TimesheetDailyEntry::getWorkedHoursInMinutes).reduce(0, Integer::sum))));
        reportBody.append("\n</body>");
        reportBody.append("\n</html>");

        emailSender.sendEmail(emailFrom, requesterEmail, "Relatório Mensal de Ponto", reportBody.toString());
    }

    private String formatHoursMinutes(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        return String.format("%02d:%02d", hours, minutes);
    }
}
