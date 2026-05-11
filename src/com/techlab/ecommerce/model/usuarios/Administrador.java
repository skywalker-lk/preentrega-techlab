package com.techlab.ecommerce.model.usuarios;

import com.techlab.ecommerce.model.roles.RolUsuario;

public class Administrador extends Usuario {
    public Administrador(
        String nombre,
        String apellido,
        String email,
        String telefono,
        RolUsuario rol
    ) {
        super(nombre, apellido, email, telefono, rol);
    }
}
