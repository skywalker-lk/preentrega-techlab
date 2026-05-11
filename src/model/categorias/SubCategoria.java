package model.categorias;

/**
 * Subcategoría de un producto, asociada a una Categoria.
 * Ejemplo: "Café" → Categoria.BEBIDAS, "Galletitas" → Categoria.ALMACEN
 */
public class SubCategoria {
    private String nombre;
    private String descripcion;
    private Categoria categoria;

    public SubCategoria(String nombre, String descripcion, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
