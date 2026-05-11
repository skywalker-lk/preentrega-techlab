package model.productos;

import java.util.Date;

/**
 * Subclase de Producto para categoria comida.
 * Agrega atributos específicos: peso, marca, subcategoría y fecha de vencimiento.
 * No declara atributos (nombre, precio, stock) — los hereda de Producto..
 */
public class Comida extends Producto {
    private double peso;
    private String subCategoria;
    private String marca;
    private Date fechaVencimiento;

    public Comida(String nombre, double precio, int stock, String categoria, double peso) {
        super(nombre, precio, stock, categoria);
        this.peso = peso;
    }

    // Getters y setters específicos de Comida
    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
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
        return super.toString() + " | Peso: " + peso + " gramos" +
                (marca != null ? " | Marca: " + marca : "");
        // +(subCategoria != null ? " | Subcategoría: " + subCategoria : "");
    }
}
