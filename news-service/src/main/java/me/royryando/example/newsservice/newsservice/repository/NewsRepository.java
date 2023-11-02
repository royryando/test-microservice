package me.royryando.example.newsservice.newsservice.repository;

import me.royryando.example.newsservice.newsservice.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findFirstByTitle(String title);
    @Query(value = "SELECT * FROM news WHERE content LIKE %:keyword% OR title LIKE %:keyword%", nativeQuery = true)
    List<News> filteredNews(String keyword);
}
