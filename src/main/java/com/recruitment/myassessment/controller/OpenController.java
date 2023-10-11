package com.recruitment.myassessment.controller;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.myassessment.dto.UserDTO;
import com.recruitment.myassessment.model.User;
import com.recruitment.myassessment.repo.UserRepo;
import com.recruitment.myassessment.service.UserService;

@RestController
@RequestMapping("/open")
public class OpenController {
    private UserService userService;
    private UserRepo userRepo;
    private ModelMapper modelMapper;

    @Autowired
    public OpenController() {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> regis(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        User user = modelMapper.map(userDTO, new TypeToken<User>() {
        }.getType());
        return userService.registrationUser(user, request);
    }
}
