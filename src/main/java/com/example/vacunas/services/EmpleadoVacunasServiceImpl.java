package com.example.vacunas.services;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.example.vacunas.models.EmpleadoVacunas;
import com.example.vacunas.repositories.EmpleadoVacunasRepository;

@Service
public class EmpleadoVacunasServiceImpl implements EmpleadoVacunasService {

    @Autowired
    EmpleadoVacunasRepository empleadoVacunasRepository;

    @Override
    public EmpleadoVacunas registrarVacunas(EmpleadoVacunas empleadoVacunas) {

        return empleadoVacunasRepository.save(empleadoVacunas);

    }

}
