package com.techlab.ecommerce.ui;

import com.techlab.ecommerce.model.roles.RolUsuario;
import com.techlab.ecommerce.model.usuarios.Usuario;
import com.techlab.ecommerce.service.UsuarioService;
import com.techlab.ecommerce.util.Validador;

import java.util.List;
import java.util.Scanner;

/**
 * Submenu de gestión de usuarios.
 * Tiene su propio loop interno y se invoca desde el menú principal.
 */
public class MenuUsuario {

    private final Scanner sc;
    private final UsuarioService usuarioService;

    public MenuUsuario(Scanner sc, UsuarioService usuarioService) {
        this.sc = sc;
        this.usuarioService = usuarioService;
    }

    public void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            try {
                switch (opcion) {
                    case 1 -> registrarUsuario();
                    case 2 -> listarUsuarios();
                    case 3 -> eliminarUsuario();
                    case 4 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida. Elija un número del 1 al 4.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println();

        } while (opcion != 4);
    }

    public void mostrarMenu() {
        System.out.println("══════════ TechLab — Gestión de Usuarios ══════════");
        System.out.println("1) Registrar usuario");
        System.out.println("2) Listar usuarios");
        System.out.println("3) Eliminar usuario");
        System.out.println("4) Volver al menú principal");
        System.out.println("════════════════════════════════════════════════════");
    }

    public void registrarUsuario() {
        System.out.println("=== Nuevo usuario ===");

        String nombre = Validador.leerTexto(sc, "Nombre: ");
        String apellido = Validador.leerTexto(sc, "Apellido: ");
        String email = Validador.leerTexto(sc, "Email: ");
        String telefono = Validador.leerTexto(sc, "Teléfono: ");

        System.out.println("Roles disponibles:");
        RolUsuario[] roles = RolUsuario.values();
        for (int i = 0; i < roles.length; i++) {
            System.out.println("  " + (i + 1) + ") " + roles[i]);
        }
        int opcionRol = Validador.leerEntero(sc, "Elija el rol: ");

        RolUsuario rol;
        if (opcionRol >= 1 && opcionRol <= roles.length) {
            rol = roles[opcionRol - 1];
        } else {
            rol = RolUsuario.CLIENTE;
            System.out.println("Opción inválida. Se asignó rol CLIENTE.");
        }

        Usuario u = usuarioService.registrar(nombre, apellido, email, telefono, rol);
        System.out.println("✔ Usuario registrado con ID " + u.getIdentificador() +
                " | Rol: " + u.getRol());
    }

    public void listarUsuarios() {
        List<Usuario> lista = usuarioService.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("--- Usuarios registrados ---");
        for (Usuario u : lista) {
            System.out.println("ID: " + u.getIdentificador() +
                    " | " + u.getNombre() + " " + u.getApellido() +
                    " | " + u.getEmail() +
                    " | Rol: " + u.getRol());
        }
        System.out.println("Total: " + lista.size() + " usuario(s).");
    }

    public void eliminarUsuario() {
        int id = Validador.leerEntero(sc, "Ingrese el ID del usuario a eliminar: ");

        Usuario u = usuarioService.obtenerPorId(id);
        System.out.println("Usuario: " + u.getNombre() + " " + u.getApellido() + " (" + u.getRol() + ")");

        String confirm = Validador.leerTexto(sc, "¿Está seguro? (s/n): ");
        if (confirm.equalsIgnoreCase("s")) {
            usuarioService.eliminar(id);
            System.out.println("✔ Usuario eliminado.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }
}
