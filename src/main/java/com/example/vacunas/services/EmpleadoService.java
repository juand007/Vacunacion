package com.example.vacunas.services;

import java.util.List;

import com.example.vacunas.entity.Credenciales;
import com.example.vacunas.models.Empleado;
import com.example.vacunas.models.EmpleadoRoles;
import com.example.vacunas.models.EmpleadoVacunas;

public interface EmpleadoService {

    public EmpleadoRoles validateLogin(Credenciales credenciales, Long id_rol);

    public List<Empleado> getAllEmpleados();
    
    public Empleado getEmpleadoById(Long id);

    public Credenciales createEmpleado(Empleado product, Long rol_id);

    public Empleado updateEmpleado(Empleado product);

    public Empleado deleteEmpleado(Long id);

    public List<Empleado> findByEstadoVacunacion(Boolean estado);

    public List<EmpleadoVacunas> findByTipoVacunacion(String tipo);

    public List<EmpleadoVacunas> findByFechaVacunacion(String fecha_inicio, String fecha_fin);

}