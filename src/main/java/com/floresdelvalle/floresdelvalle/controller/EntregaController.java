package com.floresdelvalle.floresdelvalle.controller;

import com.floresdelvalle.floresdelvalle.model.Entrega;
import com.floresdelvalle.floresdelvalle.model.Pedido;
import com.floresdelvalle.floresdelvalle.repository.EntregaRepository;
import com.floresdelvalle.floresdelvalle.service.EntregaService;
import com.floresdelvalle.floresdelvalle.service.PedidoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/entregas")
public class EntregaController {

    private final EntregaService entregaService;
    private final PedidoService pedidoService;
    private final EntregaRepository entregaRepository;

    public EntregaController(
            EntregaService entregaService,
            PedidoService pedidoService,
            EntregaRepository entregaRepository) {

        this.entregaService = entregaService;
        this.pedidoService = pedidoService;
        this.entregaRepository = entregaRepository;
    }

    @GetMapping
    public String listarEntregas(Model model) {

        model.addAttribute(
                "entregas",
                entregaService.listarTodas()
        );

        return "entregas";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {

        Entrega entrega = new Entrega();

        entrega.setEstado("Programada");

        model.addAttribute(
                "entrega",
                entrega
        );

        model.addAttribute(
                "pedidos",
                pedidoService.listarTodos()
        );

        return "formulario-entrega";
    }

    @PostMapping("/guardar")
    public String guardarEntrega(
            @ModelAttribute Entrega entrega,
            @RequestParam Long pedidoId) {

        // Buscar el pedido seleccionado
        Pedido pedido = pedidoService.buscarPorId(pedidoId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Pedido no encontrado"
                        )
                );

        /*
         * =====================================================
         * CASO 1: EDITAR UNA ENTREGA EXISTENTE
         * =====================================================
         *
         * Si la entrega tiene ID, significa que estamos editando
         * una entrega que ya existe en la base de datos.
         */

        if (entrega.getId() != null) {

            entrega.setPedido(pedido);

            entregaService.guardar(entrega);

            return "redirect:/entregas";
        }


        /*
         * =====================================================
         * CASO 2: CREAR UNA NUEVA ENTREGA
         * =====================================================
         *
         * Antes de crearla verificamos si el pedido ya tiene
         * una entrega.
         */

        java.util.Optional<Entrega> entregaExistente =
                entregaRepository.findByPedidoId(pedidoId);


        /*
         * =====================================================
         * SI YA EXISTE UNA ENTREGA
         * =====================================================
         *
         * Actualizamos la entrega existente en lugar de intentar
         * crear una segunda para el mismo pedido.
         */

        if (entregaExistente.isPresent()) {

            Entrega existente = entregaExistente.get();

            existente.setPedido(pedido);
            existente.setRepartidor(
                    entrega.getRepartidor()
            );
            existente.setRuta(
                    entrega.getRuta()
            );
            existente.setFechaEntrega(
                    entrega.getFechaEntrega()
            );
            existente.setEstado(
                    entrega.getEstado()
            );

            entregaService.guardar(existente);

            return "redirect:/entregas";
        }


        /*
         * =====================================================
         * SI NO EXISTE ENTREGA
         * =====================================================
         *
         * Creamos una nueva entrega.
         */

        entrega.setPedido(pedido);

        entregaService.guardar(entrega);

        return "redirect:/entregas";
    }


    @GetMapping("/editar/{id}")
    public String editarEntrega(
            @PathVariable Long id,
            Model model) {

        Entrega entrega = entregaService.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Entrega no encontrada"
                        )
                );

        model.addAttribute(
                "entrega",
                entrega
        );

        model.addAttribute(
                "pedidos",
                pedidoService.listarTodos()
        );

        return "formulario-entrega";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarEntrega(
            @PathVariable Long id) {

        entregaService.eliminar(id);

        return "redirect:/entregas";
    }
}