package me.royryando.example.mailservice.mailservice.service.impl;

import me.royryando.example.mailservice.mailservice.dto.MailRequestDto;
import me.royryando.example.mailservice.mailservice.model.MailLog;
import me.royryando.example.mailservice.mailservice.repository.MailLogRepository;
import me.royryando.example.mailservice.mailservice.service.MailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    public MailServiceImpl(JavaMailSender javaMailSender, MailLogRepository mailLogRepository) {
        this.javaMailSender = javaMailSender;
        this.mailLogRepository = mailLogRepository;
    }

    private final JavaMailSender javaMailSender;
    private final MailLogRepository mailLogRepository;

    @Override
    public void sendMail(MailRequestDto mailRequestDto) {
        if (
                mailRequestDto.getTo() != null && !mailRequestDto.getTo().isEmpty() &&
                        mailRequestDto.getContent() != null && !mailRequestDto.getContent().isEmpty() &&
                        mailRequestDto.getSubject() != null && !mailRequestDto.getSubject().isEmpty()
        ) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@example.com");
            message.setTo(mailRequestDto.getTo());
            message.setSubject("Newsletter - " + mailRequestDto.getSubject());
            message.setText(mailRequestDto.getContent());
            this.javaMailSender.send(message);
            this.saveFromDto(mailRequestDto);
        }
    }

    @Override
    public Page<MailLog> logs(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return mailLogRepository.getPageable(pageable);
    }

    private MailLog saveFromDto(MailRequestDto mailRequestDto) {
        var log = MailLog.builder()
                .to(mailRequestDto.getTo())
                .subject(mailRequestDto.getSubject())
                .content(mailRequestDto.getContent())
                .timestamp(System.currentTimeMillis())
                .build();

        return this.mailLogRepository.save(log);
    }
}
