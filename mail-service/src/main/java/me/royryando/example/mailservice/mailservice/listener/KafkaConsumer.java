package me.royryando.example.mailservice.mailservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.royryando.example.mailservice.mailservice.dto.MailRequestDto;
import me.royryando.example.mailservice.mailservice.service.MailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    public KafkaConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    private final MailService mailService;

    @KafkaListener(topics = "send_email_topic", groupId = "group_id")
    public void listenAsObject(String mailRequestDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MailRequestDto mailRequestDto1 = objectMapper.readValue(mailRequestDto, MailRequestDto.class);
            mailService.sendMail(mailRequestDto1);
        } catch (JsonProcessingException e) {
            // ignore
        }
        System.out.println("Email Request: " + mailRequestDto);
    }


}
