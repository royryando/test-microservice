package me.royryando.example.mailservice.mailservice.controller;

import me.royryando.example.mailservice.mailservice.dto.MailRequestDto;
import me.royryando.example.mailservice.mailservice.model.MailLog;
import me.royryando.example.mailservice.mailservice.service.MailService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class MailController {
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    private final MailService mailService;

    @PostMapping("send")
    public void send(@RequestBody MailRequestDto mailRequestDto) {
        this.mailService.sendMail(mailRequestDto);
    }

    @GetMapping("logs")
    public ResponseEntity<Page<MailLog>> logs(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit
    ) {
        return ResponseEntity.ok(this.mailService.logs(page == null ? 0 : page, limit == null ? 10 : limit));
    }

}
