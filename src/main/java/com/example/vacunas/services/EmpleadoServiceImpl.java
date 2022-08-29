package com.example.vacunas.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vacunas.repositories.EmpleadoRepository;
import com.example.vacunas.repositories.EmpleadoRolesRepository;
import com.example.vacunas.repositories.EmpleadoVacunasRepository;
import com.example.vacunas.repositories.RolRepository;
import com.example.vacunas.entity.Credenciales;
import com.example.vacunas.models.Empleado;
import com.example.vacunas.models.EmpleadoRoles;
import com.example.vacunas.models.EmpleadoRolesId;
import com.example.vacunas.models.EmpleadoVacunas;
import com.example.vacunas.models.Rol;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    EmpleadoVacunasRepository empleadoVacunasRepository;

    @Autowired
    EmpleadoRolesRepository empleadoRolesRepository;

    @Autowired
    RolRepository rolRepository;
    
    // Comprobar login
    @Override
    public EmpleadoRoles validateLogin(Credenciales credenciales, Long id_rol) {
        EmpleadoRoles empleadoRol = empleadoRolesRepository.findByUserNameAndIdRol(credenciales.getUsuario(), id_rol);
        if (empleadoRol == null) {
            return null;
        }
        if (!empleadoRol.getPassword().equals(credenciales.getPassword())) {
            return null;
        }
        return empleadoRol;
    }

    // Obtener todos los empleados
    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    // Obtener un empleado por id
    @Override
    public Empleado getEmpleadoById(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    // Crear un empleado
    @Override
    public Credenciales createEmpleado(Empleado empleado, Long rol_id) {
        // Crear un empleado
        Empleado empleadoCreate = empleadoRepository.save(empleado);

        // Generar nombre de usuario y contraseña.
        String usuario = generateUserName(empleadoCreate.getNombres(), empleadoCreate.getApellidos());
        String contraseña = generatePassword(5);
        Credenciales credenciales = new Credenciales(usuario, contraseña);

        Rol rol = rolRepository.findByIdRol(rol_id);
        EmpleadoRolesId empleadoRolesId = new EmpleadoRolesId(empleadoCreate, rol);
        EmpleadoRoles empleadoRoles =  EmpleadoRoles.builder().
        empleadoRolesId(empleadoRolesId).
        idRol(rol_id).
        idEmpleado(empleadoCreate.getIdEmpleado()).
        userName(usuario).
        password(contraseña).
        build();

        empleadoRolesRepository.save(empleadoRoles);

        return credenciales;
    }

    // Actualizar un empleado
    @Override
    public Empleado updateEmpleado(Empleado empleado) {
        Empleado empleadoDB = getEmpleadoById(empleado.getIdEmpleado());
        if (empleadoDB == null) {
            return null;
        }
        empleadoDB.setCedula(empleado.getCedula());
        empleadoDB.setNombres(empleado.getNombres());
        empleadoDB.setApellidos(empleado.getApellidos());
        empleadoDB.setCorreo(empleado.getCorreo());
        empleadoDB.setTelefono(empleado.getTelefono());
        empleadoDB.setDireccion(empleado.getDireccion());
        empleadoDB.setFechaNacimiento(empleado.getFechaNacimiento());
        empleadoDB.setEstaVacunado(empleado.getEstaVacunado());

        return empleadoRepository.save(empleadoDB);
    }

    // Eliminar un empleado
    @Override
    public Empleado deleteEmpleado(Long id) {
        Empleado empleadoDB = getEmpleadoById(id);
        if (empleadoDB == null) {
            return null;
        }
        empleadoRepository.deleteById(id);
        return empleadoDB;
    }

    // Buscar empleados por estado de vacunación
    @Override
    public List<Empleado> findByEstadoVacunacion(Boolean estado) {
        List<Empleado> lista = empleadoRepository.findByEstaVacunado(estado);
        return lista;
    }

    // Buscar empleados por tipo de vacuna
    @Override
    public List<EmpleadoVacunas> findByTipoVacunacion(String tipo) {
        List<EmpleadoVacunas> lista = empleadoVacunasRepository.findByNombreVacuna(tipo);
        System.out.println("***********************");
        System.out.println(lista);
        return lista;
    }

    // Busacar empleados por fecha de vacunación
    @Override
    public List<EmpleadoVacunas> findByFechaVacunacion(String fecha_inicio, String fecha_fin) {
        List<EmpleadoVacunas> lista = empleadoVacunasRepository.findByFechaVacunacionBetween(stringToDate(fecha_inicio),
                stringToDate(fecha_fin));
        System.out.println("***********************");
        System.out.println(lista);
        return lista;
    }

    // *****************************************/
    // Metodos adicionales
    // *****************************************/

    // Convert String to date
    public Date stringToDate(String fecha) {
        String date = fecha + " 00:00:00";
        Timestamp timestamp = Timestamp.valueOf(date);
        return timestamp;
    }

    // Generate password
    public static String generatePassword(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    // Generate username
    public String generateUserName(String nombres, String apellidos) {
        String userName = "";
        do {
            userName = getUserName(nombres, apellidos);
        } while (empleadoRolesRepository.findByUserName(userName) != null);
        return userName;
    }

    // Generate username with firstname and lastname
    public String getUserName(String nombres, String apellidos) {
        // Quitar espacios en blanco
        String userName = nombres.replace(" ", "");
        // Cortar el nombre a la mitad
        userName = userName.substring(0, userName.length() / 2);
        // Quitar espacios en blanco
        String lastname = apellidos.replace(" ", "") + "0123457689";
        // Elegir caracteres aleatorios del lastname
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(lastname.length());
        for (int i = 0; i < lastname.length(); i++)
            sb.append(lastname.charAt(rnd.nextInt(lastname.length())));
        return userName + sb.toString();
    }
}
