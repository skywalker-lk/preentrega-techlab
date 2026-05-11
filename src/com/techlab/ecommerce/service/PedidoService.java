package com.techlab.ecommerce.service;

import com.techlab.ecommerce.exception.CantidadInvalidaException;
import com.techlab.ecommerce.exception.PedidoNoEncontradoException;
import com.techlab.ecommerce.exception.StockInsuficienteException;
import com.techlab.ecommerce.model.pedidos.LineaPedido;
import com.techlab.ecommerce.model.pedidos.Pedido;
import com.techlab.ecommerce.model.productos.Producto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de servicio para la gestión de pedidos.
 *
 * Depende de ProductoService para obtener productos y descontar stock.
 * Crea pedidos con líneas, valida stock antes de confirmar y descuenta
 * el stock automáticamente al confirmar.
 */
public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();
    private final ProductoService productService;

    public PedidoService(ProductoService productService) {
        this.productService = productService;
    }

    // ----------------------------------------------------------------
    // Operaciones del CRUD
    // ----------------------------------------------------------------

    /**
     * Crea un pedido con la fecha actual.
     * Valida stock suficiente para TODOS los productos antes de descontar.
     * Si alguna validación falla, no se descuenta nada (todo o nada).
     *
     * @param idsProducto   Lista con los IDs de los productos
     * @param cantidades    Lista con las cantidades (misma posición que idsProducto)
     * @param nombreCliente Nombre del cliente
     * @return El Pedido creado
     * @throws StockInsuficienteException Si algún producto no tiene stock suficiente
     */
    public Pedido crearPedido(List<Integer> idsProducto, List<Integer> cantidades, String nombreCliente) {
        return crearPedido(idsProducto, cantidades, nombreCliente, LocalDate.now());
    }

    /**
     * Crea un pedido con una fecha personalizada.
     * Valida stock suficiente para TODOS los productos antes de descontar.
     *
     * @param idsProducto   Lista con los IDs de los productos
     * @param cantidades    Lista con las cantidades (misma posición que idsProducto)
     * @param nombreCliente Nombre del cliente
     * @param fecha         Fecha del pedido
     * @return El Pedido creado
     * @throws StockInsuficienteException Si algún producto no tiene stock suficiente
     */
    public Pedido crearPedido(List<Integer> idsProducto, List<Integer> cantidades, String nombreCliente, LocalDate fecha) {
        List<LineaPedido> lineas = new ArrayList<>();

        // Primera pasada: validar stock y construir líneas
        for (int i = 0; i < idsProducto.size(); i++) {
            int productoId = idsProducto.get(i);
            int cantidad = cantidades.get(i);

            Producto producto = productService.obtenerPorId(productoId);

            if (cantidad <= 0) {
                throw new CantidadInvalidaException(
                    "La cantidad debe ser mayor a cero para '" + producto.getNombre() + "'."
                );
            }

            if (producto.getStock() < cantidad) {
                throw new StockInsuficienteException(
                    "Stock insuficiente para '" + producto.getNombre() +
                    "'. Disponible: " + producto.getStock() +
                    ", solicitado: " + cantidad
                );
            }

            lineas.add(new LineaPedido(cantidad, producto));
        }

        // Segunda pasada: descontar stock (solo si TODAS las validaciones pasaron)
        for (LineaPedido linea : lineas) {
            Producto p = linea.getProducto();
            p.setStock(p.getStock() - linea.getCantidad());
        }

        Pedido pedido = new Pedido(nombreCliente, lineas, fecha);
        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> listarTodos() {
        return pedidos;
    }

    public Pedido obtenerPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new PedidoNoEncontradoException("No se encontró un pedido con id " + id);
    }

    public int obtenerCantidad() {
        return pedidos.size();
    }
}
