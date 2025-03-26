package com.example.biblioteca.dto;

import com.example.biblioteca.enums.RolEnum;
import lombok.Data;

import java.util.Set;

@Data
public class RegistroRequest {

    private String email;
    private String password;
    private Set<RolEnum> roles;

}
