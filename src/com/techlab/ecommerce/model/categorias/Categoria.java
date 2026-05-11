package com.techlab.ecommerce.model.categorias;

/**
 * Categorías de productos del catálogo.
 * Tipo enum: valores fijos iterables.
 */
public enum Categoria {
    BEBIDAS("Bebidas"),
    ALMACEN("Almacén"),
    GOLOSINAS("Golosinas"),
    LACTEOS("Lácteos"),
    LIMPIEZA("Limpieza");

    private final String nombre;

    Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
