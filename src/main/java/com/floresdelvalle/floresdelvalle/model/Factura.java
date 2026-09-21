package com.floresdelvalle.floresdelvalle.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioFlores;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal costosAdicionales;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private String estadoPago;

    public Factura() {
    }

    public Factura(Pedido pedido, LocalDate fecha,
                   BigDecimal precioFlores,
                   BigDecimal costosAdicionales,
                   BigDecimal total,
                   String estadoPago) {
        this.pedido = pedido;
        this.fecha = fecha;
        this.precioFlores = precioFlores;
        this.costosAdicionales = costosAdicionales;
        this.total = total;
        this.estadoPago = estadoPago;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioFlores() {
        return precioFlores;
    }

    public void setPrecioFlores(BigDecimal precioFlores) {
        this.precioFlores = precioFlores;
    }

    public BigDecimal getCostosAdicionales() {
        return costosAdicionales;
    }

    public void setCostosAdicionales(BigDecimal costosAdicionales) {
        this.costosAdicionales = costosAdicionales;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
}