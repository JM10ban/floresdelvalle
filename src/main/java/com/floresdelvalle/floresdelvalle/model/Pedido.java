package com.floresdelvalle.floresdelvalle.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCliente;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String contacto;

    @Column(nullable = false)
    private String tipoArreglo;

    @Column(nullable = false)
    private String ocasion;

    @Column(nullable = false)
    private LocalDate fechaEntrega;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal presupuesto;

    @Column(nullable = false)
    private String estado;

    public Pedido() {
    }

    public Pedido(String nombreCliente, String direccion, String contacto,
                   String tipoArreglo, String ocasion, LocalDate fechaEntrega,
                   BigDecimal presupuesto, String estado) {
        this.nombreCliente = nombreCliente;
        this.direccion = direccion;
        this.contacto = contacto;
        this.tipoArreglo = tipoArreglo;
        this.ocasion = ocasion;
        this.fechaEntrega = fechaEntrega;
        this.presupuesto = presupuesto;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTipoArreglo() {
        return tipoArreglo;
    }

    public void setTipoArreglo(String tipoArreglo) {
        this.tipoArreglo = tipoArreglo;
    }

    public String getOcasion() {
        return ocasion;
    }

    public void setOcasion(String ocasion) {
        this.ocasion = ocasion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}