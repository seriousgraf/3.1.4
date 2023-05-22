package ru.kata.spring.security.services;

import ru.kata.spring.security.models.Role;

import java.util.List;

public interface RoleService {
    public List<Role> findAllRoles();
}
