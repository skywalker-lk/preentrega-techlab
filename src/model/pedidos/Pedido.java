package model.pedidos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un pedido realizado por un cliente.
 * Contiene una lista de LineaPedido y calcula automáticamente el total.
 * El id se asigna automáticamente con un contador estático.
 */
public class Pedido {
    private static int contadorId = 1;

    private int id;
    private LocalDate fecha;
    private String cliente;
    private String estado;
    private double total;
    private List<LineaPedido> lineas;

    // Constructor sin fecha: usa la fecha actual
    public Pedido(String cliente, List<LineaPedido> lineas) {
        this(cliente, lineas, LocalDate.now());
    }

    // Constructor con fecha personalizada
    public Pedido(String cliente, List<LineaPedido> lineas, LocalDate fecha) {
        this.id = contadorId++;
        this.fecha = fecha;
        this.cliente = (cliente == null || cliente.trim().isEmpty()) ? "Mostrador" : cliente;
        this.estado = "Confirmado";
        this.lineas = new ArrayList<>(lineas);
        this.total = calcularTotal();
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.getSubtotal();
        }
        return total;
    }

    // Getters
    public int getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEstado() {
        return estado;
    }

    public double getTotal() {
        return total;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("═══ Pedido #").append(id).append(" ═══\n");
        sb.append("Fecha: ").append(fecha.format(fmt)).append("\n");
        sb.append("Cliente: ").append(cliente).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
        sb.append("Productos:\n");
        for (LineaPedido lp : lineas) {
            sb.append("  - ").append(lp.getProducto().getNombre())
              .append(" x").append(lp.getCantidad())
              .append(" @ $").append(String.format("%.2f", lp.getPrecioUnitario()))
              .append(" = $").append(String.format("%.2f", lp.getSubtotal()))
              .append("\n");
        }
        sb.append("─────────────────────────\n");
        sb.append("TOTAL: $").append(String.format("%.2f", total)).append("\n");
        return sb.toString();
    }
}
