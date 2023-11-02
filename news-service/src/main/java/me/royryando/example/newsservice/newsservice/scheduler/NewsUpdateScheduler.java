package me.royryando.example.newsservice.newsservice.scheduler;

import me.royryando.example.newsservice.newsservice.dto.NewsResponse;
import me.royryando.example.newsservice.newsservice.service.NewsService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NewsUpdateScheduler {
    public NewsUpdateScheduler(RestTemplate restTemplate, NewsService newsService) {
        this.restTemplate = restTemplate;
        this.newsService = newsService;
    }

    private final RestTemplate restTemplate;
    private final NewsService newsService;

    @Scheduled(fixedRate = 300000) // 5 minutes = 300000
    public void updateNews() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "");

        HttpEntity<Object> reqEntity = new HttpEntity<>(headers);

        ResponseEntity<NewsResponse> responseEntity = restTemplate.exchange(
                "https://newsapi.org/v2/everything?q=bitcoin",
                HttpMethod.GET,
                reqEntity,
                NewsResponse.class
        );

        NewsResponse result = responseEntity.getBody();

        this.newsService.updateDb(result);
    }

}
