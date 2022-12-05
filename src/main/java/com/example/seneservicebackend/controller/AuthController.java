package com.example.seneservicebackend.controller;


import com.example.seneservicebackend.config.JwtTokenUtil;
import com.example.seneservicebackend.entity.AuthRequest;
import com.example.seneservicebackend.entity.AuthResponse;
import com.example.seneservicebackend.entity.User;
import com.example.seneservicebackend.entity.dto.UserDto;
import com.example.seneservicebackend.exception.ResourceNotFoundException;
import com.example.seneservicebackend.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.Map;

/**
 *  - POST /api/auth/login
 *
 * 	- POST /api/auth/register
 *
 * 	- GET /api/auth/reset/{email}
 */
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private AuthenticationManager authManager;
    private JwtTokenUtil jwtUtil;
    private UserServiceImpl userService;

    public AuthController(AuthenticationManager authManager, JwtTokenUtil jwtUtil, UserServiceImpl userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request){
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);

                UserDto userDto = new UserDto(user.getId(),user.getPrenom(),user.getNom(),user.getEmail(),user.getAuthorities().toString());
                AuthResponse response = new AuthResponse(userDto, accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public  Map<String ,String>  register(@RequestBody User user) throws ValidationException {
        return userService.createUser(user);
    }
    @GetMapping("reset/{email}")
    public Map<String ,String> reset(@PathVariable(value="email") String email) throws ResourceNotFoundException, ResourceNotFoundException {
        return userService.resetPassword(email);
    }
}
