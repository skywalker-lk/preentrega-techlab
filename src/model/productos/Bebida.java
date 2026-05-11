package model.productos;

import java.util.Date;

/**
 * Subclase de Producto para categoria bebidas.
 * Agrega atributos específicos: litros, marca, subcategoría y fecha de vencimiento.
 * No declara atributos (nombre, precio, stock) — los hereda de Producto..
 */
public class Bebida extends Producto {
    private float litros;
    private String subCategoria;
    private String marca;
    private Date fechaVencimiento;

    public Bebida(String nombre, double precio, int stock, String categoria, float litros) {
        super(nombre, precio, stock, categoria);
        this.litros = litros;
    }

    // Getters y setters específicos de Bebida
    public float getLitros() {
        return litros;
    }

    public void setLitros(float litros) {
        this.litros = litros;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return super.toString() + " | Litros: " + litros + " lts" +
                (marca != null ? " | Marca: " + marca : "");
        // + (subCategoria != null ? " | Subcategoría: " + subCategoria : "");
    }
}
