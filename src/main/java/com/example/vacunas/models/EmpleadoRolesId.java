package com.example.vacunas.models;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoRolesId implements Serializable {
  
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_empleado", insertable = false, updatable = false)
    // @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Empleado empleado;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_rol", insertable = false, updatable = false)
    // @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Rol rol;

   /*  public EmpleadoRolesId(Empleado empleado, Rol rol) {
        this.empleado = empleado;
        this.rol = rol;
    } */
}
