package com.example.vacunas.models;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "rol")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rol implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rol")
    private Long idRol;

    @Column(name="nombre_rol")
    private String nombreRol;
}