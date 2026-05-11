package com.techlab.ecommerce.model.usuarios;

import com.techlab.ecommerce.model.roles.RolUsuario;

/**
 * Subclase de Usuario que representa un cliente.
 * Hereda todos los atributos de Usuario (identificador, nombre, etc.)
 * sin redeclararlos.
 */
public class Cliente extends Usuario {

    public Cliente(String nombre, String apellido, String email, String telefono, RolUsuario rol) {
        super(nombre, apellido, email, telefono, rol);
    }
}
