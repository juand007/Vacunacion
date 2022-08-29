package com.example.vacunas.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.vacunas.models.EmpleadoVacunas;

public interface EmpleadoVacunasRepository extends JpaRepository<EmpleadoVacunas, Long> {
    
    @Query(value = "SELECT * FROM empleado_vacuna inner join vacuna on empleado_vacuna.id_vacuna = vacuna.id_vacuna WHERE vacuna.nombre_vacuna = ?1", nativeQuery = true)
    List<EmpleadoVacunas> findByNombreVacuna(String tipo);

    List<EmpleadoVacunas> findByFechaVacunacionBetween(Date inicio, Date fin);

}
