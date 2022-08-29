package com.example.vacunas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vacunas.models.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    
    Rol findByIdRol(Long idRol);
}
