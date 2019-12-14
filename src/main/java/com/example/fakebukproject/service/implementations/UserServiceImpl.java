package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.Role;
import com.example.fakebukproject.domain.entities.User;
import com.example.fakebukproject.domain.models.service.LogServiceModel;
import com.example.fakebukproject.domain.models.service.UserServiceModel;
import com.example.fakebukproject.error.Constants;
import com.example.fakebukproject.repository.RoleRepository;
import com.example.fakebukproject.repository.UserRepository;
import com.example.fakebukproject.service.LogService;
import com.example.fakebukproject.service.RoleService;
import com.example.fakebukproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private Constants constants;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final LogService logService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService, RoleRepository roleRepository, LogService logService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.logService = logService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        this.roleService.putRolesInDataBase();

        User user = this.modelMapper.map(userServiceModel, User.class);
        if (this.userRepository.count() == 0) {
            user.setAuthorities(new LinkedHashSet<>(this.roleRepository.findAll()));
        } else {
            LinkedHashSet<Role> roles = new LinkedHashSet<>();
            roles.add(this.roleRepository.findByAuthority("USER"));
            user.setAuthorities(roles);
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(user.getUsername());
        logServiceModel.setDescription("User have been registered!");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.putLogInDatabase(logServiceModel);

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }


    @Override
    public UserServiceModel findUserByUserName(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));
    }

    @Override
    public UserServiceModel findUserById(String id) {
        return this.userRepository.findById(id)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USER_ID_NOT_FOUND));
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException(Constants.USERNAME_NOT_FOUND));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException(Constants.PASSWORD_IS_INCORRECT);
        }

        user.setPassword(!"".equals(userServiceModel.getPassword()) ?
                this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()) :
                user.getPassword());

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(user.getUsername());
        logServiceModel.setDescription("User profile edited");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.putLogInDatabase(logServiceModel);

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public void makeAdmin(String id) {

    }

    @Override
    public void makeUser(String id) {

    }
}
