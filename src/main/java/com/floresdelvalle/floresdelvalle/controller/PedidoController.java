package com.floresdelvalle.floresdelvalle.controller;

import com.floresdelvalle.floresdelvalle.model.Pedido;
import com.floresdelvalle.floresdelvalle.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "pedidos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        Pedido pedido = new Pedido();
        pedido.setEstado("En curso");

        model.addAttribute("pedido", pedido);

        return "formulario-pedido";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute Pedido pedido) {
        pedidoService.guardar(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/editar/{id}")
    public String editarPedido(@PathVariable Long id, Model model) {

        Pedido pedido = pedidoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));

        model.addAttribute("pedido", pedido);

        return "formulario-pedido";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return "redirect:/pedidos";
    }
}