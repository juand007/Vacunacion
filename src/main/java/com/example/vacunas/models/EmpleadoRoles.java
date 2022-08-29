package com.example.vacunas.models;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "empleado_rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoRoles implements Serializable {

    @EmbeddedId
    private EmpleadoRolesId empleadoRolesId;

    @Column(name = "id_empleado", insertable = false, updatable = false)
    private Long idEmpleado;

    @Column(name = "id_rol", insertable = false, updatable = false)
    private Long idRol;

    @Column(name = "user_name")
    private String userName;

    private String password;
    
}
