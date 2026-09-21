package com.floresdelvalle.floresdelvalle.repository;

import com.floresdelvalle.floresdelvalle.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}