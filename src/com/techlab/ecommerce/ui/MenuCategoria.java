package com.techlab.ecommerce.ui;

import com.techlab.ecommerce.exception.CategoriaNoEncontradoException;
import com.techlab.ecommerce.model.categorias.Categoria;
import com.techlab.ecommerce.model.categorias.SubCategoria;
import com.techlab.ecommerce.model.productos.Producto;
import com.techlab.ecommerce.service.CategoriaService;
import com.techlab.ecommerce.service.ProductoService;
import com.techlab.ecommerce.util.Validador;

import java.util.List;
import java.util.Scanner;

/**
 * Submenú de gestión de categorías y subcategorías.
 * Tiene su propio loop interno y se invoca desde el menú principal.
 */
public class MenuCategoria {

    private final Scanner sc;
    private final CategoriaService categoriaService;
    private final ProductoService productService;

    public MenuCategoria(Scanner sc, CategoriaService categoriaService, ProductoService productService) {
        this.sc = sc;
        this.categoriaService = categoriaService;
        this.productService = productService;
    }

    public void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarCategorias();
                    case 2 -> agregarSubCategoria();
                    case 3 -> listarSubCategorias();
                    case 4 -> listarSubCategoriasPorCategoria();
                    case 5 -> buscarSubCategoria();
                    case 6 -> eliminarSubCategoria();
                    case 7 -> filtrarProductosPorCategoria();
                    case 8 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida. Elija un número del 1 al 8.");
                }
            } catch (CategoriaNoEncontradoException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();

        } while (opcion != 8);
    }

    public void mostrarMenu() {
        System.out.println("══════════ TechLab — Gestión de Categorías ══════════");
        System.out.println("1) Listar categorías");
        System.out.println("2) Agregar subcategoría");
        System.out.println("3) Listar todas las subcategorías");
        System.out.println("4) Listar subcategorías por categoría");
        System.out.println("5) Buscar subcategoría por nombre");
        System.out.println("6) Eliminar subcategoría");
        System.out.println("7) Filtrar productos por categoría");
        System.out.println("8) Volver al menú principal");
        System.out.println("══════════════════════════════════════════════════════");
    }

    private void listarCategorias() {
        System.out.println("=== Categorías disponibles ===");
        for (Categoria c : Categoria.values()) {
            System.out.println("  " + c.name() + " → " + c.getNombre());
        }
    }

    private void agregarSubCategoria() {
        System.out.println("=== Nueva subcategoría ===");

        listarCategorias();
        System.out.println();
        String nombreCat = Validador.leerTexto(sc, "Nombre de la categoría principal (ej: BEBIDAS): ");

        Categoria categoria;
        try {
            categoria = Categoria.valueOf(nombreCat.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Categoría inválida. Use el nombre exacto (ej: BEBIDAS).");
            return;
        }

        String nombre = Validador.leerTexto(sc, "Nombre de la subcategoría: ");
        String descripcion = Validador.leerTexto(sc, "Descripción: ");

        categoriaService.agregarSubCategoria(nombre, descripcion, categoria);
        System.out.println("✔ Subcategoría '" + nombre + "' agregada en " + categoria.getNombre() + ".");
    }

    private void listarSubCategorias() {
        List<SubCategoria> lista = categoriaService.listarTodas();

        if (lista.isEmpty()) {
            System.out.println("No hay subcategorías registradas.");
            return;
        }

        System.out.println("--- Subcategorías ---");
        for (SubCategoria s : lista) {
            System.out.println("  " + s.getNombre() + " | " + s.getDescripcion() + " | [" + s.getCategoria().getNombre() + "]");
        }
        System.out.println("Total: " + lista.size() + " subcategoría(s).");
    }

    private void listarSubCategoriasPorCategoria() {
        listarCategorias();
        System.out.println();
        String nombreCat = Validador.leerTexto(sc, "Nombre de la categoría (ej: BEBIDAS): ");

        Categoria categoria;
        try {
            categoria = Categoria.valueOf(nombreCat.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Categoría inválida.");
            return;
        }

        List<SubCategoria> resultado = categoriaService.obtenerPorCategoria(categoria);

        if (resultado.isEmpty()) {
            System.out.println("No hay subcategorías en " + categoria.getNombre() + ".");
            return;
        }

        System.out.println("--- Subcategorías de " + categoria.getNombre() + " ---");
        for (SubCategoria s : resultado) {
            System.out.println("  " + s.getNombre() + " | " + s.getDescripcion());
        }
    }

    private void buscarSubCategoria() {
        String nombre = Validador.leerTexto(sc, "Nombre de la subcategoría a buscar: ");

        SubCategoria s = categoriaService.buscarSubCategoriaPorNombre(nombre);
        System.out.println("✔ Encontrada: " + s.getNombre() +
                " | " + s.getDescripcion() +
                " | [" + s.getCategoria().getNombre() + "]");
    }

    private void eliminarSubCategoria() {
        String nombre = Validador.leerTexto(sc, "Nombre de la subcategoría a eliminar: ");

        SubCategoria s = categoriaService.buscarSubCategoriaPorNombre(nombre);
        System.out.println("Subcategoría: " + s.getNombre() + " (" + s.getCategoria().getNombre() + ")");

        String confirm = Validador.leerTexto(sc, "¿Está seguro? (s/n): ");
        if (confirm.equalsIgnoreCase("s")) {
            categoriaService.eliminarSubCategoria(nombre);
            System.out.println("✔ Subcategoría eliminada.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private void filtrarProductosPorCategoria() {
        List<Producto> catalogo = productService.listarTodos();

        if (catalogo.isEmpty()) {
            System.out.println("No hay productos en el catálogo.");
            return;
        }

        listarCategorias();
        System.out.println();
        String nombreCat = Validador.leerTexto(sc, "Filtrar por categoría (ej: BEBIDAS): ");

        Categoria categoria;
        try {
            categoria = Categoria.valueOf(nombreCat.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Categoría inválida.");
            return;
        }

        List<Producto> filtrados = categoriaService.filtrarPorCategoria(catalogo, categoria);

        if (filtrados.isEmpty()) {
            System.out.println("No hay productos en la categoría " + categoria.getNombre() + ".");
            return;
        }

        System.out.println("--- Productos en " + categoria.getNombre() + " ---");
        for (Producto p : filtrados) {
            System.out.println("  " + p);
        }
        System.out.println("Total: " + filtrados.size() + " producto(s).");
    }
}
