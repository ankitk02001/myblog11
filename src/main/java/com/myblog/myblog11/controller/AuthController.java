package com.myblog.myblog11.controller;

import com.myblog.myblog11.entity.User;
import com.myblog.myblog11.payload.SignUpDto;
import com.myblog.myblog11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto singupDto){

        if(userRepository.existsByUsername(singupDto.getUsername())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(singupDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user=new User();
        user.setName(singupDto.getName());
        user.setUsername(singupDto.getUsername());
        user.setEmail(singupDto.getEmail());
        user.setPassword(passwordEncoder.encode(singupDto.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("User registered sucessfully", HttpStatus.OK);
    }
}
