package com.example.seneservicebackend.service.impl;

import com.example.seneservicebackend.entity.Role;
import com.example.seneservicebackend.entity.User;
import com.example.seneservicebackend.exception.ResourceNotFoundException;
import com.example.seneservicebackend.repository.RoleRepository;
import com.example.seneservicebackend.repository.UserRepository;
import com.example.seneservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on ::"+userId));

        return user;
    }

    @Override
    public User updateUser(Long userId, User userDetails) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()){
           throw  new ResourceNotFoundException("User not found on ::"+userId);
        }
        User user = userOptional.get();

        user.setPrenom(userDetails.getPrenom());
        user.setNom(userDetails.getNom());
        user.setEmail(userDetails.getEmail());
        user.getRoles().clear();
        Role role = roleRepository.findByName(userDetails.getRole());
        user.addRole(role);
        userRepository.save(user);
        return user;
    }

    @Override
    public  Map<String ,String>  createUser(User user) throws ValidationException {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ValidationException("User exists");
        }
        Role role = roleRepository.findByName(user.getRole());
        user.addRole(role);
        user.setPassword (encoder.encode(user.getPassword()));


        userRepository.save(user);
        Map<String , String> response = new HashMap<>();
        response.put("message", "Inscription réuissie");
        return response;

    }

    @Override
    public Map<String ,String> resetPassword(String email) throws ResourceNotFoundException {
        if(!userRepository.findByEmail(email).isPresent()){
            throw new ResourceNotFoundException("Utilisateur introuvable");
        }
        Map<String , String> response = new HashMap<>();
        response.put("message", "http://localhost:4200/reset/"+email);
        return response;

    }

    @Override
    public Map<String ,Boolean> deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Map<String , Boolean> response = new HashMap<>();
        if(!userOptional.isPresent()){
            response.put("delete", Boolean.FALSE);
        } else{
            response.put("delete", Boolean.TRUE);
            User user = userOptional.get();
            userRepository.delete(user);
        }

        return response ;
    }

    @Override
    public Map<String, String> changeEmail(String email) {
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findByEmail(user1.getEmail());
        User user = userOptional.get();
        user.setEmail(email);
        userRepository.save (user);
        Map<String , String> response = new HashMap<>();
        response.put("message", "adresse e-mail modifiée veuillez vous reconnectez svp !");
        return response;
    }

    @Override
    public Map<String, String> changePassword(String password, String newPassword) {
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOptional = userRepository.findByEmail(user1.getEmail());
        User user = userOptional.get();
        Map<String , String> response = new HashMap<>();
        String encodedPassword = encoder.encode(password);
        Boolean bool = encoder.matches(user.getPassword(),encodedPassword);
        if(bool){
            user.setPassword(encoder.encode(newPassword));
            userRepository.save (user);
            response.put("message", "Votre mot de passe est modifié!");
            return response;
        }
        response.put("errorMessage", "Mot de passe incorrect !");
        return response;
    }


}
