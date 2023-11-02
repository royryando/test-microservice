package me.royryando.example.mailservice.mailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.royryando.example.mailservice.mailservice.model.MailLog;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailRequestDto {
    private String to;
    private String subject;
    private String content;
}
