package com.techlab.ecommerce.service;

import com.techlab.ecommerce.exception.ClienteNoEncontradoException;
import com.techlab.ecommerce.model.roles.RolUsuario;
import com.techlab.ecommerce.model.usuarios.Cliente;
import com.techlab.ecommerce.model.usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa de servicio para la gestión de usuarios.
 * Administra el registro, listado y eliminación de usuarios.
 * Soporta herencia: el método registrar(String, String, String, String, RolUsuario)
 * crea una instancia de Cliente, Administrador,
 * o un Usuario genérico según el rol.
 * guardamos cualquier subclase como tipo Usuario.
 */
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private static int contadorId = 1;

    // ----------------------------------------------------------------
    // Operaciones del CRUD
    // ----------------------------------------------------------------

    /**
     * Registra un nuevo usuario.
     *
     * @param nombre    Nombre del usuario.
     * @param apellido  Apellido del usuario.
     * @param email     Correo electrónico.
     * @param telefono  Teléfono.
     * @param rol       Rol del usuario (determina si se crea una subclase).
     * @return El usuario creado (puede ser Cliente u otra subclase).
     */
    public Usuario registrar(String nombre, String apellido, String email, String telefono, RolUsuario rol) {
        Usuario u;

        // Usamos polimorfismo: según el rol, creamos una subclase distinta
        switch (rol) {
            case CLIENTE -> u = new Cliente(nombre, apellido, email, telefono, rol);
            default -> u = new Usuario(nombre, apellido, email, telefono, rol);
        }

        u.setIdentificador(contadorId++);
        usuarios.add(u);
        return u;
    }

    public List<Usuario> listarTodos() {
        return usuarios;
    }

    public Usuario obtenerPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getIdentificador() == id) {
                return u;
            }
        }
        throw new ClienteNoEncontradoException("No se encontró un usuario con id " + id);
    }

    public void eliminar(int id) {
        Usuario u = obtenerPorId(id);
        usuarios.remove(u);
    }

    public int obtenerCantidad() {
        return usuarios.size();
    }
}
