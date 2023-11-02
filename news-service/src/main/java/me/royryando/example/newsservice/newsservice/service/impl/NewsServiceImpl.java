package me.royryando.example.newsservice.newsservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import me.royryando.example.newsservice.newsservice.dto.MailRequestDto;
import me.royryando.example.newsservice.newsservice.dto.NewsResponse;
import me.royryando.example.newsservice.newsservice.dto.SendNewsDto;
import me.royryando.example.newsservice.newsservice.model.News;
import me.royryando.example.newsservice.newsservice.repository.NewsRepository;
import me.royryando.example.newsservice.newsservice.service.NewsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    public NewsServiceImpl(KafkaTemplate<String, String> kafkaTemplate, NewsRepository newsRepository, RedisTemplate<String, String> redisTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.newsRepository = newsRepository;
        this.redisTemplate = redisTemplate;
    }

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewsRepository newsRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String CACHE_LATEST_NEWS_KEY = "latest_news";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendEmail(SendNewsDto sendNewsDto) {
        var newsOpt = this.newsRepository.findById(sendNewsDto.getNewsId());
        if (newsOpt.isPresent()) {
            var news = newsOpt.get();

            MailRequestDto mailRequestDto = new MailRequestDto();
            mailRequestDto.setTo(sendNewsDto.getTo());
            mailRequestDto.setSubject(news.getTitle());
            mailRequestDto.setContent(news.getContent());

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                this.kafkaTemplate.send("send_email_topic", objectMapper.writeValueAsString(mailRequestDto));
            } catch (JsonProcessingException e) {
                // ignore
            }
        }
    }

    @Override
    public List<News> allNews() {
        var all = this.newsRepository.findAll();
        return all.stream()
                .peek(news -> {
                    if (news.getTitle() == null || news.getTitle().isEmpty()) {
                        news.setTitle("-no-title-");
                    }
                })
                .filter(o -> o.getId() != 1)
                .collect(Collectors.toList());
    }

    @Override
    public List<News> latestNews() {
        return getCachedNews();
    }

    @Override
    public List<News> filteredNews(String keyword) {
        return this.newsRepository.filteredNews(keyword);
    }

    @Override
    @Transactional
    public void updateDb(NewsResponse newsResponse) {
        List<News> willCached = new ArrayList<>();
        for (int i = 0; i < newsResponse.getArticles().size(); i++) {
            var news = newsResponse.getArticles().get(i);

            var check = this.newsRepository.findFirstByTitle(news.getTitle());
            if (check.isEmpty()) {
                var savedNews = this.newsRepository.save(News.builder()
                        .title(news.getTitle())
                        .content(news.getDescription())
                        .build());

                if (i >= newsResponse.getArticles().size() - 5) {
                    willCached.add(savedNews);
                }
            } else {
                if (i >= newsResponse.getArticles().size() - 5) {
                    willCached.add(check.get());
                }
            }
        }

        cacheNews(willCached);
    }

    private List<News> getCachedNews() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        var res = ops.get(CACHE_LATEST_NEWS_KEY);
        try {
            return objectMapper.readValue(res, new TypeReference<List<News>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void cacheNews(List<News> news) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        try {
            String cached = objectMapper.writeValueAsString(news);
            ops.set(CACHE_LATEST_NEWS_KEY, cached, 10, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
