package com.example.seneservicebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthRequest {
    @Email @Length(min = 5, max = 50)
    private String email;
    @Length(min = 5)
    private String password;
    private String nom;
    private String prenom;
    private String role;
}
