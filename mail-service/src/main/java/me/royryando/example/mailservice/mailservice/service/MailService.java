package me.royryando.example.mailservice.mailservice.service;

import me.royryando.example.mailservice.mailservice.dto.MailRequestDto;
import me.royryando.example.mailservice.mailservice.model.MailLog;
import org.springframework.data.domain.Page;

public interface MailService {
    void sendMail(MailRequestDto mailRequestDto);
    Page<MailLog> logs(int page, int limit);
}
