package com.example.demo;

import lombok.Data;

@Data
public class CustomUserResponseDTO {

    private Integer id;
    private String userName;
    private String companyName;
    private Geo geo;
}
