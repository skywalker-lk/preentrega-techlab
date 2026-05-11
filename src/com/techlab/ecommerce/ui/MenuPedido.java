package com.techlab.ecommerce.ui;

import com.techlab.ecommerce.exception.PedidoNoEncontradoException;
import com.techlab.ecommerce.model.pedidos.Pedido;
import com.techlab.ecommerce.service.PedidoService;
import com.techlab.ecommerce.util.Validador;

import java.util.List;
import java.util.Scanner;

/**
 * Sub-menú de gestión de pedidos.
 * Tiene su propio loop interno y se invoca desde el menú principal.
 */
public class MenuPedido {

    private final Scanner sc;
    private final PedidoService pedidoService;

    public MenuPedido(Scanner sc, PedidoService pedidoService) {
        this.sc = sc;
        this.pedidoService = pedidoService;
    }

    public void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarPedidos();
                    case 2 -> buscarPedido();
                    case 3 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida. Elija un número del 1 al 3.");
                }
            } catch (PedidoNoEncontradoException e) {
                System.out.println(e.getMessage());
            }

            System.out.println();

        } while (opcion != 3);
    }

    public void mostrarMenu() {
        System.out.println("══════════ TechLab — Gestión de Pedidos ══════════");
        System.out.println("1) Listar pedidos");
        System.out.println("2) Buscar pedido por ID");
        System.out.println("3) Volver al menú principal");
        System.out.println("═══════════════════════════════════════════════════");
    }

    public void listarPedidos() {
        List<Pedido> lista = pedidoService.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        System.out.println("=== Pedidos registrados ===");
        for (Pedido p : lista) {
            System.out.println(p);
            System.out.println();
        }
        System.out.println("Total: " + lista.size() + " pedido(s).");
    }

    public void buscarPedido() {
        int id = Validador.leerEntero(sc, "Ingrese el ID del pedido: ");
        Pedido p = pedidoService.obtenerPorId(id);
        System.out.println(p);
    }
}
