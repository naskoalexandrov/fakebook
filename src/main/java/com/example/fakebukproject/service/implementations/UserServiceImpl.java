package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.User;
import com.example.fakebukproject.domain.models.service.LogServiceModel;
import com.example.fakebukproject.domain.models.service.UserServiceModel;
import com.example.fakebukproject.repository.UserRepository;
import com.example.fakebukproject.service.LogService;
import com.example.fakebukproject.service.RoleService;
import com.example.fakebukproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final LogService logService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService, LogService logService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.logService = logService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        this.roleService.putRolesInDataBase();
        if(this.userRepository.count() == 0){
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        }else{
            userServiceModel.setAuthorities(new LinkedHashSet<>());

            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(user.getUsername());
        logServiceModel.setDescription("User have been registered!");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.putLogInDatabase(logServiceModel);

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }
}
