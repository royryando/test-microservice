package me.royryando.example.newsservice.newsservice.dto;

import lombok.Data;

@Data
public class SendNewsDto {
    private String to;
    private Long newsId;
}
