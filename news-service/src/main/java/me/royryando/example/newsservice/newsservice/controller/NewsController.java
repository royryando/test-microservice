package me.royryando.example.newsservice.newsservice.controller;

import me.royryando.example.newsservice.newsservice.dto.SendNewsDto;
import me.royryando.example.newsservice.newsservice.model.News;
import me.royryando.example.newsservice.newsservice.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class NewsController {
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    private final NewsService newsService;

    @GetMapping("all")
    public ResponseEntity<List<News>> allNews() {
        var result = this.newsService.allNews();
        return ResponseEntity.ok(result);
    }

    @GetMapping("search")
    public ResponseEntity<List<News>> searchNews(@RequestParam String keyword) {
        var result = this.newsService.filteredNews(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("latest")
    public ResponseEntity<List<News>> latestNews() {
        var result = this.newsService.latestNews();
        return ResponseEntity.ok(result);
    }

    @PostMapping("send-to")
    public ResponseEntity<String> sendNews(@RequestBody SendNewsDto sendNewsDto) {
        this.newsService.sendEmail(sendNewsDto);
        return ResponseEntity.ok("sending email!");
    }

}
