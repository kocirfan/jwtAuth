package com.kocirfan.jwtauth.services;

import com.kocirfan.jwtauth.models.User;

import  com.kocirfan.jwtauth.models.Role;
import java.util.List;

public interface UserService {

    User save(User user);

    Role save(Role role);

    //rolü user a ata
    void addRoleTo(String username, String role);

    //sürekli user ı isimle çek
    User get(String username);

    List<User> list();


}
