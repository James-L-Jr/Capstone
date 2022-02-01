package com.hcl.udemy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.udemy.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



}