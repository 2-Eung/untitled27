package com.example.untitled27.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer id;
    private String username;
    private String password;
}