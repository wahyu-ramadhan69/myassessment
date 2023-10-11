package com.recruitment.myassessment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitment.myassessment.dto.UserDTO;
import com.recruitment.myassessment.model.Usr;
import com.recruitment.myassessment.service.UsrService;

@RestController
@RequestMapping("/open")
public class OpenController {
    private UsrService usrService;
    private ModelMapper modelMapper;
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public OpenController(UsrService usrService, ModelMapper modelMapper, AuthenticationManager authenticationManager,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usrService = usrService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/v1/regis")
    public ResponseEntity<Object> regis(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        Usr usr = modelMapper.map(userDTO, new TypeToken<Usr>() {
        }.getType());
        ;
        return usrService.registrationUser(usr, request);
    }
}
