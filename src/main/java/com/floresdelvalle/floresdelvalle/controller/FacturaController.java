package com.floresdelvalle.floresdelvalle.controller;

import com.floresdelvalle.floresdelvalle.model.Factura;
import com.floresdelvalle.floresdelvalle.model.Pedido;
import com.floresdelvalle.floresdelvalle.repository.FacturaRepository;
import com.floresdelvalle.floresdelvalle.service.FacturaService;
import com.floresdelvalle.floresdelvalle.service.PedidoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final PedidoService pedidoService;
    private final FacturaRepository facturaRepository;

    public FacturaController(
            FacturaService facturaService,
            PedidoService pedidoService,
            FacturaRepository facturaRepository) {

        this.facturaService = facturaService;
        this.pedidoService = pedidoService;
        this.facturaRepository = facturaRepository;
    }


    @GetMapping
    public String listarFacturas(Model model) {

        model.addAttribute(
                "facturas",
                facturaService.listarTodas()
        );

        return "facturas";
    }


    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {

        Factura factura = new Factura();

        factura.setFecha(LocalDate.now());
        factura.setPrecioFlores(BigDecimal.ZERO);
        factura.setCostosAdicionales(BigDecimal.ZERO);
        factura.setTotal(BigDecimal.ZERO);
        factura.setEstadoPago("Pendiente");

        model.addAttribute(
                "factura",
                factura
        );

        model.addAttribute(
                "pedidos",
                pedidoService.listarTodos()
        );

        return "formulario-factura";
    }


    @PostMapping("/guardar")
    public String guardarFactura(
            @ModelAttribute Factura factura,
            @RequestParam Long pedidoId) {

        /*
         * =====================================================
         * BUSCAR PEDIDO
         * =====================================================
         */

        Pedido pedido = pedidoService.buscarPorId(pedidoId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Pedido no encontrado"
                        )
                );

        /*
         * =====================================================
         * VALORES POR DEFECTO
         * =====================================================
         */

        if (factura.getPrecioFlores() == null) {
            factura.setPrecioFlores(BigDecimal.ZERO);
        }

        if (factura.getCostosAdicionales() == null) {
            factura.setCostosAdicionales(BigDecimal.ZERO);
        }

        if (factura.getFecha() == null) {
            factura.setFecha(LocalDate.now());
        }

        /*
         * =====================================================
         * CALCULAR TOTAL
         * =====================================================
         */

        BigDecimal total = factura.getPrecioFlores()
                .add(factura.getCostosAdicionales());

        factura.setTotal(total);

        /*
         * =====================================================
         * CASO 1: EDITAR FACTURA
         * =====================================================
         */

        if (factura.getId() != null) {

            Factura facturaExistente =
                    facturaService.buscarPorId(factura.getId())
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Factura no encontrada"
                                    )
                            );

            facturaExistente.setPedido(pedido);
            facturaExistente.setFecha(factura.getFecha());
            facturaExistente.setPrecioFlores(
                    factura.getPrecioFlores()
            );
            facturaExistente.setCostosAdicionales(
                    factura.getCostosAdicionales()
            );
            facturaExistente.setTotal(
                    factura.getTotal()
            );
            facturaExistente.setEstadoPago(
                    factura.getEstadoPago()
            );

            facturaService.guardar(facturaExistente);

            return "redirect:/facturas";
        }


        /*
         * =====================================================
         * CASO 2: NUEVA FACTURA
         * =====================================================
         *
         * Verificamos si el pedido ya tiene factura.
         */

        Optional<Factura> facturaExistente =
                facturaRepository.findByPedidoId(pedidoId);


        /*
         * =====================================================
         * SI YA EXISTE UNA FACTURA
         * =====================================================
         *
         * Actualizamos la existente en lugar de crear otra.
         */

        if (facturaExistente.isPresent()) {

            Factura existente = facturaExistente.get();

            existente.setPedido(pedido);
            existente.setFecha(factura.getFecha());
            existente.setPrecioFlores(
                    factura.getPrecioFlores()
            );
            existente.setCostosAdicionales(
                    factura.getCostosAdicionales()
            );
            existente.setTotal(
                    factura.getTotal()
            );
            existente.setEstadoPago(
                    factura.getEstadoPago()
            );

            facturaService.guardar(existente);

            return "redirect:/facturas";
        }


        /*
         * =====================================================
         * SI NO EXISTE FACTURA
         * =====================================================
         */

        factura.setPedido(pedido);

        facturaService.guardar(factura);

        return "redirect:/facturas";
    }


    @GetMapping("/editar/{id}")
    public String editarFactura(
            @PathVariable Long id,
            Model model) {

        Factura factura = facturaService.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Factura no encontrada"
                        )
                );

        model.addAttribute(
                "factura",
                factura
        );

        model.addAttribute(
                "pedidos",
                pedidoService.listarTodos()
        );

        return "formulario-factura";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarFactura(
            @PathVariable Long id) {

        facturaService.eliminar(id);

        return "redirect:/facturas";
    }
}
