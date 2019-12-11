package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.User;
import com.example.fakebukproject.domain.models.service.UserServiceModel;
import com.example.fakebukproject.repository.UserRepository;
import com.example.fakebukproject.service.RoleService;
import com.example.fakebukproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
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

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }
}
