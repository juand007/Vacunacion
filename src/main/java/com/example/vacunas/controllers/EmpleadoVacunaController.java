package com.example.vacunas.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.vacunas.models.EmpleadoRoles;
import com.example.vacunas.models.EmpleadoVacunas;
import com.example.vacunas.services.EmpleadoRolesService;
import com.example.vacunas.services.EmpleadoVacunasService;

@RestController
@RequestMapping("/vacunacion/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoVacunaController {

    @Autowired
    private EmpleadoVacunasService empleadoVacunasService;

    @Autowired
    private EmpleadoRolesService empleadoRolesService;

    @PostMapping(value = "/registrarVacunas/{user_name}")
    public ResponseEntity<Map<String, Object>> registrarVacunas(@Valid @RequestBody EmpleadoVacunas empleadoVacunas,
            @PathVariable("user_name") String user_name) {

        // Objeto de la respuesta
        Map<String, Object> result = new HashMap<>();

        //Verificar si el usuario existe
        EmpleadoRoles empleadoRol = empleadoRolesService.buscarNombreUsuario(user_name);
        if (empleadoRol == null) {
            result.put("ok", false);
            result.put("msg", "El empleado no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        //Registrar vacunas
        empleadoVacunas.setIdEmpleado(empleadoRol.getIdEmpleado());
        EmpleadoVacunas empleadoVacunasNuevo = empleadoVacunasService.registrarVacunas(empleadoVacunas);
        if (empleadoVacunasNuevo == null) {
            result.put("ok", false);
            result.put("msg", "Error al registrar la vacuna");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        // Formatear la respuesta
        result.put("ok", true);
        result.put("msg", "Vacuna registrada");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
