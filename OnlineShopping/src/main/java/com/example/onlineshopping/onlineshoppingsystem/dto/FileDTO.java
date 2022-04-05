package com.example.onlineshopping.onlineshoppingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FileDTO {
    private String fileId;
    private String name;
    private String type;
    private LocalDateTime uploadTime;
    private String url;
}
