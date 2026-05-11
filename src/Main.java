import java.util.Scanner;

import exception.PedidoNoEncontradoException;
import exception.ProductoNoEncontradoException;
import exception.StockInsuficienteException;

import model.productos.Bebida;
import model.productos.Comida;
import model.productos.Producto;

import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;

import ui.MenuProducto;
import ui.MenuUsuario;
import util.Validador;

/**
 * Clase principal: punto de entrada del programa.
 *
 * Su única responsabilidad es ORQUESTAR la ejecución:
 * - Crear el servicio y el menú.
 * - Controlar el bucle del menú hasta que el usuario decida salir.
 * - Atrapar las excepciones que puedan surgir y mostrar el mensaje
 * al usuario sin que el programa se cierre.
 *
 * Toda la interacción con el usuario (mostrar el menú, pedir datos,
 * mostrar resultados) está delegada en MenuProducto. La lógica de
 * negocio (validar, guardar, buscar, actualizar, eliminar) está en
 * ProductoService. Cada clase tiene una sola responsabilidad clara.
 */
public class Main {

    public static void main(String[] args) {
        // Creamos las dependencias una sola vez y las compartimos
        // durante toda la ejecución del programa.
        ProductoService productService = new ProductoService();
        PedidoService pedidoService = new PedidoService(productService);
        Scanner sc = new Scanner(System.in);
        MenuProducto menu = new MenuProducto(sc, productService, pedidoService);

        // Carga inicial de productos de prueba.
        // Comentar esta línea si se quiere arrancar con el catálogo vacío.
        cargarDatosDePrueba(productService);

        int opcion;

        // do-while garantiza que el menú se muestre al menos una
        // vez. Se repite hasta que el usuario elige la opción 7.
        do {
            menu.mostrarMenu();
            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            // Cada opcion del menú envuelta en try/catch.
            // Si una operacion lanza una excepcion (producto
            // inexistente, datos inválidos, etc.), el programa NO
            // se crashea: muestra el mensaje y vuelve a mostrar el menú.
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
                // Capturamos nuestras excepciones personalizadas.
                // Cada una tiene su propio mensaje, definido al
                // momento de lanzarla en el servicio o el validador.
                System.out.println(e.getMessage());
            } catch (Exception e) {
                // Exception es la que lanza el
                // Validador para datos genéricos inválidos
                // (nombre vacío, precio negativo, etc.).
                System.out.println("Dato inválido: " + e.getMessage());
            }

            System.out.println(); // línea en blanco entre operaciones

        } while (opcion != 8);

        sc.close();
    }

    // ----------------------------------------------------------------
    // Carga de datos de prueba
    // ----------------------------------------------------------------
    // Precarga el catálogo con algunos productos al iniciar el programa.
    // Es útil durante el desarrollo para no tener que cargar productos
    // a mano cada vez que se ejecuta la aplicación. Para empezar con
    // el catálogo vacío, basta con comentar la llamada a este método
    // en el main.
    //
    // Notar que la carga usa el método público service.guardar(): no
    // accedemos directamente a la lista interna del servicio. Esto
    // respeta el encapsulamiento y nos asegura que los productos de
    // prueba pasen por las mismas validaciones que cualquier otro.
    private static void cargarDatosDePrueba(ProductoService service) {
        service.guardar(new Bebida("Café molido 500g", 4500, 30, "Bebidas", 0.5f));
        service.guardar(new Bebida("Yerba mate 1kg", 3200, 50, "Bebidas", 1.0f));
        service.guardar(new Comida("Galletitas dulces", 1850, 100, "Almacén", 200));
        service.guardar(new Comida("Aceite de oliva 500ml", 6700, 20, "Almacén", 500));
        service.guardar(new Comida("Chocolate amargo 70%", 2900, 15, "Golosinas", 100));
        System.out.println("✔ Se cargaron 5 productos de prueba.\n");
    }
}
