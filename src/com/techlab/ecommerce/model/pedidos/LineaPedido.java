package com.techlab.ecommerce.model.pedidos;

import com.techlab.ecommerce.model.productos.Producto;

public class LineaPedido {
    private static int identificador = 1;
    private Producto producto;

    private String descripcion;
    private int cantidad;
    private double precioUnitario;

    private double subtotal;
    private String estado;

    public LineaPedido(int cantidad, Producto producto) {
        this.producto = producto;

        this.descripcion = producto.getNombre();
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();

        this.calcularSubtotal();
        this.estado = "Pendiente";
        this.incrementarIdentificador();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getIdentificador() {
        return identificador;
    }

    public static void incrementarIdentificador() {
        identificador++;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void calcularSubtotal() {
        this.subtotal = this.cantidad * this.precioUnitario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
