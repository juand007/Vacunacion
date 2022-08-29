package com.example.vacunas.entity;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Credenciales {

    @NotEmpty(message = "El nombre de usuario es obligatorio")
    String usuario;

    @NotEmpty(message = "La contraseña es obligatorio")
    String password;

    public Credenciales(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }
}
