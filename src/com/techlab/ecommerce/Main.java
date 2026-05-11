package com.techlab.ecommerce;

import com.techlab.ecommerce.exception.PedidoNoEncontradoException;
import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.exception.StockInsuficienteException;
import com.techlab.ecommerce.model.productos.Bebida;
import com.techlab.ecommerce.model.productos.Comida;
import com.techlab.ecommerce.model.productos.Producto;
import com.techlab.ecommerce.service.CategoriaService;
import com.techlab.ecommerce.service.PedidoService;
import com.techlab.ecommerce.service.ProductoService;
import com.techlab.ecommerce.service.UsuarioService;
import com.techlab.ecommerce.ui.MenuCategoria;
import com.techlab.ecommerce.ui.MenuProducto;
import com.techlab.ecommerce.ui.MenuUsuario;
import com.techlab.ecommerce.util.Validador;

import java.util.Scanner;

/**
 * Clase principal: punto de entrada del programa.
 * Su única responsabilidad es ORQUESTAR la ejecución:
 * - Crear los servicios y menús.
 * - Controlar el bucle del menú principal.
 * - Atrapar las excepciones que puedan surgir.
 * Toda la interacción con el usuario está delegada en los menús.
 */
public class Main {

    public static void main(String[] args) {
        // ── Servicios ──
        ProductoService productService = new ProductoService();
        PedidoService pedidoService = new PedidoService(productService);
        CategoriaService categoriaService = new CategoriaService();
        UsuarioService usuarioService = new UsuarioService();

        Scanner sc = new Scanner(System.in);

        // ── Menús ──
        MenuProducto menuProducto = new MenuProducto(sc, productService, pedidoService);
        MenuCategoria menuCategoria = new MenuCategoria(sc, categoriaService, productService);
        MenuUsuario menuUsuario = new MenuUsuario(sc, usuarioService);

        cargarDatosDePrueba(productService);

        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            try {
                switch (opcion) {
                    case 1 -> menuProductos(sc, menuProducto);
                    case 2 -> menuPedidos(sc, menuProducto);
                    case 3 -> menuCategoria.ejecutar();
                    case 4 -> menuUsuario.ejecutar();
                    case 5 -> System.out.println("¡Hasta la vista...!");
                    default -> System.out.println("Opción inválida. Elija un número del 1 al 5.");
                }
            } catch (ProductoNoEncontradoException | StockInsuficienteException | PedidoNoEncontradoException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Dato inválido: " + e.getMessage());
            }

            System.out.println();

        } while (opcion != 5);

        sc.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("══════════ TechLab — Sistema de Gestión ══════════");
        System.out.println("1) Gestionar productos");
        System.out.println("2) Gestionar pedidos");
        System.out.println("3) Gestionar categorías");
        System.out.println("4) Gestionar usuarios");
        System.out.println("5) Salir");
        System.out.println("═══════════════════════════════════════════════════");
    }

    // ── Submenú de Productos ──

    private static void menuProductos(Scanner sc, MenuProducto menu) {
        int opcion;
        do {
            System.out.println("══════════ TechLab — Productos ══════════");
            System.out.println("1) Agregar producto");
            System.out.println("2) Listar productos");
            System.out.println("3) Buscar producto");
            System.out.println("4) Actualizar producto");
            System.out.println("5) Eliminar producto");
            System.out.println("6) Volver al menú principal");
            System.out.println("══════════════════════════════════════════");

            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            try {
                switch (opcion) {
                    case 1 -> menu.agregarProducto();
                    case 2 -> menu.listarProductos();
                    case 3 -> menu.buscarProducto();
                    case 4 -> menu.actualizarProducto();
                    case 5 -> menu.eliminarProducto();
                    case 6 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (ProductoNoEncontradoException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();

        } while (opcion != 6);
    }

    // ── Submenú de Pedidos ──

    private static void menuPedidos(Scanner sc, MenuProducto menu) {
        int opcion;
        do {
            System.out.println("══════════ TechLab — Pedidos ══════════");
            System.out.println("1) Crear pedido");
            System.out.println("2) Listar pedidos");
            System.out.println("3) Volver al menú principal");
            System.out.println("═══════════════════════════════════════");

            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            try {
                switch (opcion) {
                    case 1 -> menu.crearPedido();
                    case 2 -> menu.listarPedidos();
                    case 3 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (ProductoNoEncontradoException | StockInsuficienteException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();

        } while (opcion != 3);
    }

    // ── Datos de prueba ──

    private static void cargarDatosDePrueba(ProductoService service) {
        service.guardar(new Bebida("Café molido 500g", 4500, 30, "Bebidas", 0.5f));
        service.guardar(new Bebida("Yerba mate 1kg", 3200, 50, "Bebidas", 1.0f));
        service.guardar(new Comida("Galletitas dulces", 1850, 100, "Almacén", 200));
        service.guardar(new Comida("Aceite de oliva 500ml", 6700, 20, "Almacén", 500));
        service.guardar(new Comida("Chocolate amargo 70%", 2900, 15, "Golosinas", 100));
        System.out.println("✔ Se cargaron 5 productos de prueba.\n");
    }
}
