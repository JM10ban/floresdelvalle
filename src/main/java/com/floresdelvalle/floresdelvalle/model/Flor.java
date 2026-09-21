package com.floresdelvalle.floresdelvalle.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "flores")
public class Flor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String variedad;

    @Column(nullable = false)
    private Integer cantidadDisponible;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioCompra;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioVenta;

    public Flor() {
    }

    public Flor(String tipo, String color, String variedad,
                Integer cantidadDisponible, BigDecimal precioCompra,
                BigDecimal precioVenta) {
        this.tipo = tipo;
        this.color = color;
        this.variedad = variedad;
        this.cantidadDisponible = cantidadDisponible;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }
}