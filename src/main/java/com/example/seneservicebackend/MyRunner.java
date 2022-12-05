package com.example.seneservicebackend;


import com.example.seneservicebackend.entity.Role;
import com.example.seneservicebackend.entity.User;
import com.example.seneservicebackend.repository.RoleRepository;
import com.example.seneservicebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        Role admin = new Role("ROLE_ADMIN");
        Role client = new Role("ROLE_CLIENT");
        Role prestaire = new Role("ROLE_PRESTAIRE");

        Role roleFind = roleRepository.findByName(admin.getName());
        if(roleFind!=null) {
            return;
        }else{
            roleRepository.save(admin);
            roleRepository.save(client);
            roleRepository.save(prestaire);
        }
        List<Role> roles = roleRepository.findAll();

        roles.forEach((role) ->
        {
            System.out.println(role.getName());
        });
        User user = new User();
        user.setNom("admin");
        user.setPrenom("admin");
        user.setEmail("admin@admin.com");
        user.setPassword("admin");
        user.setRole("admin");
        Role role = roleRepository.findByName("ROLE_ADMIN");
        user.addRole(role);
        user.setPassword (encoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println(user.getPrenom());

    }
}
