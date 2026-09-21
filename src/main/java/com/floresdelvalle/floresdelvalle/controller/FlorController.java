package com.floresdelvalle.floresdelvalle.controller;

import com.floresdelvalle.floresdelvalle.model.Flor;
import com.floresdelvalle.floresdelvalle.service.FlorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario")
public class FlorController {

    private final FlorService florService;

    public FlorController(FlorService florService) {
        this.florService = florService;
    }

    @GetMapping
    public String listarFlores(Model model) {
        model.addAttribute("flores", florService.listarTodas());
        return "inventario";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("flor", new Flor());
        return "formulario-flor";
    }

    @PostMapping("/guardar")
    public String guardarFlor(@ModelAttribute Flor flor) {
        florService.guardar(flor);
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editarFlor(@PathVariable Long id, Model model) {
        Flor flor = florService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Flor no encontrada"));

        model.addAttribute("flor", flor);
        return "formulario-flor";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarFlor(@PathVariable Long id) {
        florService.eliminar(id);
        return "redirect:/inventario";
    }
}