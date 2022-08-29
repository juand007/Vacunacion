package com.example.vacunas.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;

@Entity
@Table(name = "empleado_vacuna")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoVacunas implements Serializable {

    //@EmbeddedId
    //private EmpleadoVacunasId empleadoVacunasId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado_vacuna")
    private Long idEmpleadoVacuna;
    
    @Column(name = "id_empleado")
    private Long idEmpleado;

    @Column(name = "id_vacuna")
    private Long idVacuna;

    @Column(name = "fecha_vacunacion")
    private Date fechaVacunacion;

    @Column(name = "numero_dosis")
    private Integer numeroDosis;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_empleado", insertable = false, updatable = false)
    // @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Empleado empleado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_vacuna", insertable = false, updatable = false)
    // @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vacuna vacuna;

}
