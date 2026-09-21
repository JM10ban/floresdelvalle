package com.floresdelvalle.floresdelvalle.service;

import com.floresdelvalle.floresdelvalle.model.Entrega;
import com.floresdelvalle.floresdelvalle.repository.EntregaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntregaService {

    private final EntregaRepository entregaRepository;

    public EntregaService(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    public List<Entrega> listarTodas() {
        return entregaRepository.findAll();
    }

    public Optional<Entrega> buscarPorId(Long id) {
        return entregaRepository.findById(id);
    }

    public Entrega guardar(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    public void eliminar(Long id) {
        entregaRepository.deleteById(id);
    }
}