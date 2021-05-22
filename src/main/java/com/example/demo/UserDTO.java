package com.example.demo;

import lombok.Data;

@Data
public class UserDTO {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

}
