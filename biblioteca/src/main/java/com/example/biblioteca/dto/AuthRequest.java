package com.example.biblioteca.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthRequest {

    private String email;
    private String password;

}
