package com.floresdelvalle.floresdelvalle.service;

import com.floresdelvalle.floresdelvalle.model.Flor;
import com.floresdelvalle.floresdelvalle.repository.FlorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlorService {

    private final FlorRepository florRepository;

    public FlorService(FlorRepository florRepository) {
        this.florRepository = florRepository;
    }

    public List<Flor> listarTodas() {
        return florRepository.findAll();
    }

    public Optional<Flor> buscarPorId(Long id) {
        return florRepository.findById(id);
    }

    public Flor guardar(Flor flor) {
        return florRepository.save(flor);
    }

    public void eliminar(Long id) {
        florRepository.deleteById(id);
    }
}