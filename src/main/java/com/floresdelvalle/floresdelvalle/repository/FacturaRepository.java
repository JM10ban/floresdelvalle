package com.floresdelvalle.floresdelvalle.repository;

import com.floresdelvalle.floresdelvalle.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findByPedidoId(Long pedidoId);

}