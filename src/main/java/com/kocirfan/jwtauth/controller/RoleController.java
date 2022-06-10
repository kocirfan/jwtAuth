package com.kocirfan.jwtauth.controller;

import com.kocirfan.jwtauth.models.Role;
import com.kocirfan.jwtauth.repository.RoleRepository;
import com.kocirfan.jwtauth.repository.UserRepository;
import com.kocirfan.jwtauth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final UserService userService;

    //rol kaydet
    @PostMapping
    public Role save(@RequestBody Role role){
        return userService.save(role);
    }


}
