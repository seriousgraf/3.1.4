package ru.kata.spring.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.security.models.Role;
import ru.kata.spring.security.repositories.RoleRepository;

import java.util.List;
@Service
public class RoleServiceImp implements RoleService{
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
