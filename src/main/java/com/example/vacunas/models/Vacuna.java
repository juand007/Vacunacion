package com.example.vacunas.models;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "vacuna")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vacuna implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_vacuna")
    private Long idVacuna;

    @Column(name="nombre_vacuna")
    private String nombreVacuna;
}