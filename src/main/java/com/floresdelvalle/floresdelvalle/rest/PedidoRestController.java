package com.floresdelvalle.floresdelvalle.rest;

import com.floresdelvalle.floresdelvalle.model.Pedido;
import com.floresdelvalle.floresdelvalle.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(
    name = "Pedidos",
    description = "Operaciones CRUD para la gestión de pedidos"
)
public class PedidoRestController {

    private final PedidoService pedidoService;

    public PedidoRestController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(
        summary = "Listar todos los pedidos",
        description = "Obtiene todos los pedidos registrados."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @Operation(
        summary = "Consultar pedido por ID",
        description = "Obtiene un pedido específico mediante su identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(
            @Parameter(description = "Identificador del pedido", required = true)
            @PathVariable Long id) {

        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear pedido",
        description = "Registra un nuevo pedido."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.guardar(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @Operation(
        summary = "Actualizar pedido",
        description = "Actualiza los datos de un pedido existente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(
            @Parameter(description = "Identificador del pedido", required = true)
            @PathVariable Long id,
            @RequestBody Pedido pedido) {

        return pedidoService.buscarPorId(id)
                .map(pedidoExistente -> {

                    pedidoExistente.setNombreCliente(pedido.getNombreCliente());
                    pedidoExistente.setDireccion(pedido.getDireccion());
                    pedidoExistente.setContacto(pedido.getContacto());
                    pedidoExistente.setTipoArreglo(pedido.getTipoArreglo());
                    pedidoExistente.setOcasion(pedido.getOcasion());
                    pedidoExistente.setFechaEntrega(pedido.getFechaEntrega());
                    pedidoExistente.setPresupuesto(pedido.getPresupuesto());
                    pedidoExistente.setEstado(pedido.getEstado());

                    return ResponseEntity.ok(
                            pedidoService.guardar(pedidoExistente)
                    );
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar pedido",
        description = "Elimina un pedido mediante su identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pedido eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Identificador del pedido", required = true)
            @PathVariable Long id) {

        return pedidoService.buscarPorId(id)
                .map(pedido -> {
                    pedidoService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}