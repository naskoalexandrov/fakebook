package com.example.fakebukproject.service;

import com.example.fakebukproject.domain.models.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {

    void putRolesInDataBase();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
