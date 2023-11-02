package me.royryando.example.newsservice.newsservice.service;

import me.royryando.example.newsservice.newsservice.dto.MailRequestDto;
import me.royryando.example.newsservice.newsservice.dto.NewsResponse;
import me.royryando.example.newsservice.newsservice.dto.SendNewsDto;
import me.royryando.example.newsservice.newsservice.model.News;

import java.util.List;

public interface NewsService {
    void sendEmail(SendNewsDto sendNewsDto);
    List<News> allNews();
    List<News> latestNews();
    List<News> filteredNews(String keyword);
    void updateDb(NewsResponse newsResponse);
}
