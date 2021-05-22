package com.example.demo;

import lombok.Data;

import java.util.List;

@Data
public class CustomUserResponse {

    private List<CustomUserResponseDTO> customUserResponseDTOSList;
}
