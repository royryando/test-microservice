package me.royryando.example.newsservice.newsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
    private String status;
    private Integer totalResults;
    private List<NewsResponseDetail> articles;
}
