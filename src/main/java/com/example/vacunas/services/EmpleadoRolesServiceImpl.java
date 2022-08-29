package com.example.vacunas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vacunas.models.EmpleadoRoles;
import com.example.vacunas.repositories.EmpleadoRolesRepository;

@Service
public class EmpleadoRolesServiceImpl implements EmpleadoRolesService {

    @Autowired
    EmpleadoRolesRepository empleadoRolesRepository;
    
    @Override
    public EmpleadoRoles buscarNombreUsuario(String user_name) {
        return empleadoRolesRepository.findByUserName(user_name);
    }
    
}
