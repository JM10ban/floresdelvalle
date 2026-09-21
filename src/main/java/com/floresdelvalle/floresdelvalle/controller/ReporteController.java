package com.floresdelvalle.floresdelvalle.controller;

import com.floresdelvalle.floresdelvalle.model.Factura;
import com.floresdelvalle.floresdelvalle.model.Pedido;
import com.floresdelvalle.floresdelvalle.repository.FacturaRepository;
import com.floresdelvalle.floresdelvalle.repository.PedidoRepository;
import com.floresdelvalle.floresdelvalle.repository.EntregaRepository;
import com.floresdelvalle.floresdelvalle.repository.FlorRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    private final FacturaRepository facturaRepository;
    private final PedidoRepository pedidoRepository;
    private final EntregaRepository entregaRepository;
    private final FlorRepository florRepository;

    public ReporteController(
            FacturaRepository facturaRepository,
            PedidoRepository pedidoRepository,
            EntregaRepository entregaRepository,
            FlorRepository florRepository) {

        this.facturaRepository = facturaRepository;
        this.pedidoRepository = pedidoRepository;
        this.entregaRepository = entregaRepository;
        this.florRepository = florRepository;
    }

    @GetMapping
    public String mostrarReportes(Model model) {

        List<Factura> facturas = facturaRepository.findAll();
        List<Pedido> pedidos = pedidoRepository.findAll();

        BigDecimal ingresosTotales = facturas.stream()
                .map(Factura::getTotal)
                .filter(total -> total != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long facturasPagadas = facturas.stream()
                .filter(f -> "Pagado".equalsIgnoreCase(f.getEstadoPago()))
                .count();

        long pedidosEnCurso = pedidos.stream()
                .filter(p -> "En curso".equalsIgnoreCase(p.getEstado()))
                .count();

        long pedidosCompletados = pedidos.stream()
                .filter(p -> "Completado".equalsIgnoreCase(p.getEstado()))
                .count();

        long pedidosEntregados = pedidos.stream()
                .filter(p -> "Entregado".equalsIgnoreCase(p.getEstado()))
                .count();

        model.addAttribute("totalFlores", florRepository.count());
        model.addAttribute("totalPedidos", pedidos.size());
        model.addAttribute("totalEntregas", entregaRepository.count());
        model.addAttribute("totalFacturas", facturas.size());

        model.addAttribute("ingresosTotales", ingresosTotales);
        model.addAttribute("facturasPagadas", facturasPagadas);

        model.addAttribute("pedidosEnCurso", pedidosEnCurso);
        model.addAttribute("pedidosCompletados", pedidosCompletados);
        model.addAttribute("pedidosEntregados", pedidosEntregados);

        return "reportes";
    }
}