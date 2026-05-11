package com.techlab.ecommerce;

import com.techlab.ecommerce.exception.PedidoNoEncontradoException;
import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.exception.StockInsuficienteException;
import com.techlab.ecommerce.model.productos.Bebida;
import com.techlab.ecommerce.model.productos.Comida;
import com.techlab.ecommerce.model.productos.Producto;
import com.techlab.ecommerce.service.PedidoService;
import com.techlab.ecommerce.service.ProductoService;
import com.techlab.ecommerce.ui.MenuProducto;
import com.techlab.ecommerce.util.Validador;

import java.util.Scanner;

/**
 * Clase principal: punto de entrada del programa.
 *
 * Su única responsabilidad es ORQUESTAR la ejecución:
 * - Crear el servicio y el menú.
 * - Controlar el bucle del menú hasta que el usuario decida salir.
 * - Atrapar las excepciones que puedan surgir y mostrar el mensaje
 *   al usuario sin que el programa se cierre.
 *
 * Toda la interacción con el usuario está delegada en MenuProducto.
 * La lógica de negocio está en ProductoService.
 */
public class Main {

    public static void main(String[] args) {
        ProductoService productService = new ProductoService();
        PedidoService pedidoService = new PedidoService(productService);
        Scanner sc = new Scanner(System.in);
        MenuProducto menu = new MenuProducto(sc, productService, pedidoService);

        cargarDatosDePrueba(productService);

        int opcion;

        do {
            menu.mostrarMenu();
            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            try {
                switch (opcion) {
                    case 1 -> menu.agregarProducto();
                    case 2 -> menu.listarProductos();
                    case 3 -> menu.buscarProducto();
                    case 4 -> menu.actualizarProducto();
                    case 5 -> menu.eliminarProducto();
                    case 6 -> menu.crearPedido();
                    case 7 -> menu.listarPedidos();
                    case 8 -> System.out.println("Hasta la vista...!");
                    default -> System.out.println("Opción inválida. Elija un número del 1 al 8.");
                }
            } catch (ProductoNoEncontradoException | StockInsuficienteException | PedidoNoEncontradoException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Dato inválido: " + e.getMessage());
            }

            System.out.println();

        } while (opcion != 8);

        sc.close();
    }

    private static void cargarDatosDePrueba(ProductoService service) {
        service.guardar(new Bebida("Café molido 500g", 4500, 30, "Bebidas", 0.5f));
        service.guardar(new Bebida("Yerba mate 1kg", 3200, 50, "Bebidas", 1.0f));
        service.guardar(new Comida("Galletitas dulces", 1850, 100, "Almacén", 200));
        service.guardar(new Comida("Aceite de oliva 500ml", 6700, 20, "Almacén", 500));
        service.guardar(new Comida("Chocolate amargo 70%", 2900, 15, "Golosinas", 100));
        System.out.println("✔ Se cargaron 5 productos de prueba.\n");
    }
}
