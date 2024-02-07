package com.myblog.myblog11.controller;

import com.myblog.myblog11.entity.Role;
import com.myblog.myblog11.entity.User;
import com.myblog.myblog11.payload.SignUpDto;
import com.myblog.myblog11.repository.RoleRepository;
import com.myblog.myblog11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
   private RoleRepository RoleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto singupDto){

        if(userRepository.existsByUsername(singupDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(singupDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user=new User();
        user.setName(singupDto.getName());
        user.setUsername(singupDto.getUsername());
        user.setEmail(singupDto.getEmail());
        user.setPassword(passwordEncoder.encode(singupDto.getPassword()));
        Role roles = RoleRepository.findByName(singupDto.getRoleType()).get();
        //coverting this data to set and then added to entity roles
        Set<Role>  convertRoleToSet=new HashSet<>();
        convertRoleToSet.add(roles);
        user.setRoles(convertRoleToSet);
       // user.setRoles(Collections.singleton(roles));//another way for  above conversion
        userRepository.save(user);
        return new ResponseEntity<>("User registered sucessfully", HttpStatus.OK);
    }
}
