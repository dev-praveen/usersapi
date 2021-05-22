package com.example.demo;

import lombok.Data;

@Data
public class UsersTodoDTO {

    private Integer userId;
    private Integer id;
    private String title;
    private Boolean completed;
}
