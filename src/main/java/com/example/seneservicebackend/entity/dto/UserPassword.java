package com.example.seneservicebackend.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserPassword {
    private String password;
    private String newPassword;
}
