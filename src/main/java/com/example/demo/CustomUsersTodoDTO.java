package com.example.demo;

import lombok.Data;

@Data
public class CustomUsersTodoDTO implements Comparable<CustomUsersTodoDTO> {

    private String userName;
    private String companyName;
    private Integer completedTasks;
    private Integer notCompletedTasks;

    @Override
    public int compareTo(CustomUsersTodoDTO usersTodoDTO) {
        return this.getUserName().compareTo(usersTodoDTO.getUserName());
    }
}
