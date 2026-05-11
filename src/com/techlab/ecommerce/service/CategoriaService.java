package com.techlab.ecommerce.service;

import com.techlab.ecommerce.model.categorias.Categoria;
import com.techlab.ecommerce.model.categorias.SubCategoria;
import com.techlab.ecommerce.model.productos.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestión de subcategorías y filtrado por categoría.
 * Las categorías principales son fijas Categoria enum.
 * Las subcategorías se administran desde acá y pueden asociarse
 * a productos para filtros más específicos.
 */
public class CategoriaService {

    private final List<SubCategoria> subcategorias = new ArrayList<>();

    // ----------------------------------------------------------------
    // Operaciones del CRUD
    // ----------------------------------------------------------------

    /**
     * Agrega una subcategoría.
     */
    public void agregarSubCategoria(String nombre, String descripcion, Categoria categoria) {
        subcategorias.add(new SubCategoria(nombre, descripcion, categoria));
    }

    /**
     * Devuelve todas las subcategorías de una categoría.
     */
    public List<SubCategoria> obtenerPorCategoria(Categoria categoria) {
        List<SubCategoria> resultado = new ArrayList<>();
        for (SubCategoria s : subcategorias) {
            if (s.getCategoria() == categoria) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    /**
     * Filtra una lista de productos por categoría.
     */
    public List<Producto> filtrarPorCategoria(List<Producto> productos, Categoria categoria) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            // getCategoria() devuelve String por ahora — al migrar Producto
            // a Categoria enum se comparará directamente p.getCategoria() == categoria
            if (categoria.getNombre().equalsIgnoreCase(p.getCategoria())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<SubCategoria> listarTodas() {
        return subcategorias;
    }
}
