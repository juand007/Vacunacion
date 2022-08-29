package com.example.vacunas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vacunas.models.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findByEstaVacunado(Boolean estado);

    
}
