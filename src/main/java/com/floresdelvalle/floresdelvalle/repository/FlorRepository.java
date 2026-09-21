package com.floresdelvalle.floresdelvalle.repository;

import com.floresdelvalle.floresdelvalle.model.Flor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlorRepository extends JpaRepository<Flor, Long> {
}