package com.kocirfan.jwtauth.services.Impl;

import com.kocirfan.jwtauth.models.User;
import com.kocirfan.jwtauth.repository.RoleRepository;
import com.kocirfan.jwtauth.repository.UserRepository;
import com.kocirfan.jwtauth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import com.kocirfan.jwtauth.models.Role;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    //kullanıcıya role verme
    @Override
    public void addRoleTo(String username, String roleName) {
        // kullanıcı ce rol al
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        // user a rol ekle
        user.getRoles().add(role);
        userRepository.save(user);

    }

    @Override
    public User get(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }
}
