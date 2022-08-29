package com.example.vacunas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vacunas.models.EmpleadoRoles;
import com.example.vacunas.models.EmpleadoRolesId;

public interface EmpleadoRolesRepository extends JpaRepository<EmpleadoRoles, EmpleadoRolesId> {

    EmpleadoRoles findByUserName(String userName);

    EmpleadoRoles findByUserNameAndIdRol(String userName, Long idRol);

}
