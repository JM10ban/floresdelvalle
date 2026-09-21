package com.floresdelvalle.floresdelvalle.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private String repartidor;

    @Column(nullable = false)
    private String ruta;

    @Column(nullable = false)
    private LocalDate fechaEntrega;

    @Column(nullable = false)
    private String estado;

    public Entrega() {
    }

    public Entrega(Pedido pedido, String repartidor, String ruta,
                   LocalDate fechaEntrega, String estado) {
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.ruta = ruta;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
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

    public String getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}