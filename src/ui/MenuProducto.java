package ui;

import model.pedidos.LineaPedido;
import model.pedidos.Pedido;
import model.productos.Bebida;
import model.productos.Comida;
import model.productos.Producto;
import service.PedidoService;
import service.ProductoService;
import util.Validador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Maneja la interacción con el usuario a través del menú de consola.
 * <p>
 * Esta clase es responsable de:
 * - Mostrar el menú al usuario.
 * - Pedirle los datos necesarios para cada operación.
 * - Mostrar los resultados o mensajes informativos.
 * <p>
 * No contiene lógica de negocio: delega en {@link ProductoService} y
 * {@link PedidoService}. El Main controla el flujo y atrapa excepciones.
 */
public class MenuProducto {

    private final Scanner sc;
    private final ProductoService productService;
    private final PedidoService pedidoService;

    public MenuProducto(Scanner sc, ProductoService productService, PedidoService pedidoService) {
        this.sc = sc;
        this.productService = productService;
        this.pedidoService = pedidoService;
    }

    // ----------------------------------------------------------------
    // Menú principal
    // ----------------------------------------------------------------

    public void mostrarMenu() {
        System.out.println("══════════ TechLab — Gestión de Productos ══════════");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar producto");
        System.out.println("4) Actualizar producto");
        System.out.println("5) Eliminar producto");
        System.out.println("6) Crear un pedido");
        System.out.println("7) Listar pedidos");
        System.out.println("8) Salir");
        System.out.println("════════════════════════════════════════════════════");
    }

    // ----------------------------------------------------------------
    // Operaciones del CRUD de Productos
    // ----------------------------------------------------------------

    public void agregarProducto() {
        System.out.println("=== Nuevo producto ===");
        System.out.println("Tipo: #1 Producto genérico,  #2 Bebida,  #3 Comida");
        int tipo = Validador.leerEntero(sc, "Elija una opción: ");

        // Campos comunes a todos los tipos
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        String categoria = Validador.leerTexto(sc, "Categoría: ");

        Producto p;

        switch (tipo) {
            case 2 -> {
                float litros = (float) Validador.leerDouble(sc, "Litros: ");
                p = new Bebida(nombre, precio, stock, categoria, litros);
            }
            case 3 -> {
                double peso = Validador.leerDouble(sc, "Peso en gramos: ");
                p = new Comida(nombre, precio, stock, categoria, peso);
            }
            default -> p = new Producto(nombre, precio, stock, categoria);
        }

        Producto guardado = productService.guardar(p);
        System.out.println("Producto agregado con id " + guardado.getId());
    }

    public void listarProductos() {
        List<Producto> lista = productService.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        System.out.println("--- Catálogo de productos ---");
        for (Producto p : lista) {
            System.out.println(p);
        }
        System.out.println("Total: " + lista.size() + " producto(s).");
    }

    public void buscarProducto() {
        System.out.println("Buscar por ID #1 o por Nombre #2");
        int tipo = Validador.leerEntero(sc, "Elija una opción: ");

        if (tipo == 1) {
            int id = Validador.leerEntero(sc, "Ingrese el id del producto: ");
            Producto p = productService.obtenerPorId(id);
            System.out.println("Encontrado: " + p);

        } else if (tipo == 2) {
            String nombre = Validador.leerTexto(sc, "Ingrese el nombre o parte del nombre: ");
            List<Producto> resultados = productService.buscarPorNombre(nombre);

            if (resultados.isEmpty()) {
                System.out.println("No se encontraron productos con ese nombre.");
            } else {
                System.out.println("Resultados (" + resultados.size() + "):");
                for (Producto p : resultados) {
                    System.out.println(p);
                }
            }

        } else {
            System.out.println("Opción inválida.");
        }
    }

    public void actualizarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a actualizar: ");

        Producto actual = productService.obtenerPorId(id);
        System.out.println("Datos actuales: " + actual);

        System.out.println("--- Ingrese los nuevos datos ---");
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        String categoria = Validador.leerTexto(sc, "Categoría: ");

        Producto datos = new Producto(nombre, precio, stock, categoria);
        Producto actualizado = productService.actualizar(id, datos);

        System.out.println("Producto actualizado: " + actualizado);
    }

    public void eliminarProducto() {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a eliminar: ");

        // Mostramos el producto antes de eliminar
        Producto p = productService.obtenerPorId(id);
        System.out.println("Producto a eliminar: " + p);

        String confirm = Validador.leerTexto(sc, "Seguro desea eliminar este producto? (s/n): ");
        if (confirm.equalsIgnoreCase("s")) {
            productService.eliminar(id);
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    // ----------------------------------------------------------------
    // Operaciones de Pedidos
    // ----------------------------------------------------------------

    public void crearPedido() {
        List<Producto> catalogo = productService.listarTodos();

        if (catalogo.isEmpty()) {
            System.out.println("No hay productos disponibles. Agregue productos primero.");
            return;
        }

        System.out.println("=== Crear pedido ===");

        // Mostrar productos disponibles
        System.out.println("Productos disponibles:");
        for (Producto p : catalogo) {
            System.out.println("  " + p);
        }

        int cantidadItems = Validador.leerEntero(sc, "\n Cuantos productos distintos va a cargar? ");

        List<Integer> idsProducto = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();

        for (int i = 1; i <= cantidadItems; i++) {
            System.out.println("\n Producto #" + i + ":");
            int idProducto = Validador.leerEntero(sc, "  ID del producto: ");

            // Verificar que existe (obtenerPorId lanza excepción si no)
            Producto p = productService.obtenerPorId(idProducto);
            System.out.println("  -> " + p.getNombre() + " | Stock disponible: " + p.getStock());

            int cantidad = Validador.leerEntero(sc, "  Cantidad: ");

            idsProducto.add(idProducto);
            cantidades.add(cantidad);
        }

        String cliente = Validador.leerTexto(sc, "\n Nombre del cliente : ");

        // Preguntar si quiere fecha personalizada
        Pedido pedido;
        String fechaCustom = Validador.leerTexto(sc, "¿Desea ingresar una fecha personalizada? (s/n): ");
        if (fechaCustom.equalsIgnoreCase("s")) {
            LocalDate fecha = Validador.leerFecha(sc, "Ingrese la fecha (dd/MM/yyyy): ");
            pedido = pedidoService.crearPedido(idsProducto, cantidades, cliente, fecha);
        } else {
            pedido = pedidoService.crearPedido(idsProducto, cantidades, cliente);
        }

        System.out.println("\n✔ Pedido creado exitosamente:");
        System.out.println(pedido);
    }

    public void listarPedidos() {
        List<Pedido> lista = pedidoService.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }

        System.out.println("--- Pedidos registrados ---");
        for (Pedido p : lista) {
            System.out.println(p);
            System.out.println();
        }
        System.out.println("Total: " + lista.size() + " pedido(s).");
    }
}
