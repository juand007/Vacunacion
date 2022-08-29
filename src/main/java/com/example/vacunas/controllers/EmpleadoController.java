package com.example.vacunas.controllers;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.vacunas.entity.Credenciales;
import com.example.vacunas.models.Empleado;
import com.example.vacunas.models.EmpleadoRoles;
import com.example.vacunas.models.EmpleadoVacunas;
import com.example.vacunas.services.EmpleadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/vacunacion/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private HttpServletRequest request;

    // **************************************
    // Operaciones CRUD de empleados
    // **************************************

    // Comprobar login
    @PostMapping(value = "/login/{id_rol}")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody Credenciales credenciales,
            @PathVariable("id_rol") Long id_rol) {
        EmpleadoRoles empleadoRol = empleadoService.validateLogin(credenciales, id_rol);
        // Objeto de la respuesta
        Map<String, Object> result = new HashMap<>();

        if (empleadoRol == null) {
            // Formatear la respuesta
            result.put("ok", false);
            result.put("error", "Login incorrecto");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        // Formatear la respuesta
        result.put("ok", true);
        result.put("msg", "Login correcto");

        return ResponseEntity.ok(result);
    }

    // Obtener todos los empleados
    @GetMapping()
    public ResponseEntity<Map<String, List<Empleado>>> listEmpleado() {
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        // Formatear la respuesta
        Map<String, List<Empleado>> result = new HashMap<>();
        result.put("empleados", empleados);

        return ResponseEntity.ok(result);
    }

    // Crear un empleado
    @PostMapping(value = "/{id_rol}")
    public ResponseEntity<Map<String, Object>> createEmpleado(@Valid @RequestBody Empleado empleado,
            @PathVariable("id_rol") Long id_rol, BindingResult result) {
        // Objeto de la respuesta
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            // Formatear la respuesta
            response.put("ok", false);
            response.put("error", "No se pudo crear el empleado");
            response.put("msg", this.formatMessage(result));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        // Crear un empleado
        Credenciales credenciales = empleadoService.createEmpleado(empleado, id_rol);

        // Formatear la respuesta
        response.put("credenciales", credenciales);

        return ResponseEntity.ok(response);
    }

    // Editar un empleado por id
    @PutMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> updateEmpleado(@PathVariable("id") Long id,
            @Valid @RequestBody Empleado empleado) {
        // Objeto de la respuesta
        Map<String, Object> response = new HashMap<>();

        empleado.setIdEmpleado(id);
        Empleado empleadoUpdate = empleadoService.updateEmpleado(empleado);
        if (empleadoUpdate == null) {
            // Formatear la respuesta
            response.put("ok", false);
            response.put("error", "No se pudo editar al empleado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Formatear la respuesta
        response.put("empleado", empleadoUpdate);

        return ResponseEntity.ok(response);
    }

    // Eliminar un empleado por id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmpleado(@PathVariable("id") Long id) {
        // Objeto de la respuesta
        Map<String, Object> response = new HashMap<>();

        Empleado empleadoDelete = empleadoService.deleteEmpleado(id);
        if (empleadoDelete == null) {
             // Formatear la respuesta
            response.put("ok", false);
            response.put("error", "No se pudo eliminar al empleado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Formatear la respuesta
        response.put("empleado", empleadoDelete);

        return ResponseEntity.ok(response);
    }

    // **************************************
    // Operaciones de filtrado de empleados
    // **************************************

    // Obtener empleados por estado de vacunacion
    @GetMapping(value = "/estado/{estado}")
    public ResponseEntity<Map<String, List<Empleado>>> getEmpleadosByEstado(@PathVariable("estado") Boolean estado) {
        List<Empleado> empleados = empleadoService.findByEstadoVacunacion(estado);

        // Formatear la respuesta
        Map<String, List<Empleado>> result = new HashMap<>();
        result.put("empleados", empleados);

        return ResponseEntity.ok(result);
    }

    // Obtener empleados por tipo de vacuna
    @GetMapping(value = "/tipovacuna/{tipo}")
    public ResponseEntity<Map<String, List<EmpleadoVacunas>>> getEmpleadosByTipoVacuna(
            @PathVariable("tipo") String tipo) {
        List<EmpleadoVacunas> empleados = empleadoService.findByTipoVacunacion(tipo);

        // Formatear la respuesta
        Map<String, List<EmpleadoVacunas>> result = new HashMap<>();
        result.put("empleados", empleados);

        return ResponseEntity.ok(result);
    }

    // Obtener empleados por rango de fecha de vacunacion
    @GetMapping(value = "/fechavacuna/{fecha1}/{fecha2}")
    public ResponseEntity<Map<String, List<EmpleadoVacunas>>> getEmpleadosByFechaVacuna(
            @PathVariable("fecha1") String fecha1,
            @PathVariable("fecha2") String fecha2) {
        List<EmpleadoVacunas> empleados = empleadoService.findByFechaVacunacion(fecha1, fecha2);

        // Formatear la respuesta
        Map<String, List<EmpleadoVacunas>> result = new HashMap<>();
        result.put("empleados", empleados);

        return ResponseEntity.ok(result);
    }

    /****************************************************/
    /* Manejo de errores */
    /****************************************************/

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("El error es:" + jsonString);
        return jsonString;
    }

    /* Obtener parametros del request */
    private String getParams(String nameParam) {
        String param = request.getParameter(nameParam);
        if (param == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Param " + nameParam + " not found");
        }
        return param;
    }

}
