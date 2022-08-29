package com.example.vacunas.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;

@Entity
@Table(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empleado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_empleado")
    private Long idEmpleado;

    @NotEmpty(message = "La cédula es obligatorio")
    @Size( min = 10 , max = 10, message = "El tamaño de la cédula debe ser de 10 dígitos")
    @Column(unique=true)
    private String cedula;

    @NotEmpty(message = "Los nombres son obligatorio")
    @Pattern(regexp = "[A-Za-z]*", message="Los nombres solo puede tener letras")
    private String nombres;

    @NotEmpty(message = "Los apellidos son obligatorio")
    @Pattern(regexp = "[A-Za-z]*", message="Los apellidos solo puede tener letras")
    private String apellidos;

    @NotEmpty(message = "El correo es obligatorio")
    @Email(message = "El correo no es válido")
    private String correo;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;
    private String direccion;
    private String telefono;
    @Column(name = "esta_vacunado")
    private Boolean estaVacunado;

}
