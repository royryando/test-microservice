package me.royryando.example.newsservice.newsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseDetail {
    private String title;
    private String description;
}
