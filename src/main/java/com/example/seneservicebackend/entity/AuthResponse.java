package com.example.seneservicebackend.entity;

import com.example.seneservicebackend.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private UserDto user;
    private  String token;

}
