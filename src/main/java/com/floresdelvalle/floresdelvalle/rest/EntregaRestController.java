package com.floresdelvalle.floresdelvalle.rest;

import com.floresdelvalle.floresdelvalle.model.Entrega;
import com.floresdelvalle.floresdelvalle.service.EntregaService;

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
@RequestMapping("/api/entregas")
@Tag(
    name = "Entregas",
    description = "Operaciones CRUD para la gestión de entregas"
)
public class EntregaRestController {

    private final EntregaService entregaService;

    public EntregaRestController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @Operation(
        summary = "Listar todas las entregas",
        description = "Obtiene todas las entregas registradas."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de entregas obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Entrega>> listarTodas() {
        return ResponseEntity.ok(entregaService.listarTodas());
    }

    @Operation(
        summary = "Consultar entrega por ID",
        description = "Obtiene una entrega específica mediante su identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Entrega encontrada"),
        @ApiResponse(responseCode = "404", description = "Entrega no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Entrega> buscarPorId(
            @Parameter(description = "Identificador de la entrega", required = true)
            @PathVariable Long id) {

        return entregaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear entrega",
        description = "Registra una nueva entrega."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Entrega creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Entrega> guardar(@RequestBody Entrega entrega) {
        Entrega nuevaEntrega = entregaService.guardar(entrega);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEntrega);
    }

    @Operation(
        summary = "Actualizar entrega",
        description = "Actualiza los datos de una entrega existente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Entrega actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Entrega no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Entrega> actualizar(
            @Parameter(description = "Identificador de la entrega", required = true)
            @PathVariable Long id,
            @RequestBody Entrega entrega) {

        return entregaService.buscarPorId(id)
                .map(entregaExistente -> {

                    entregaExistente.setPedido(entrega.getPedido());
                    entregaExistente.setRepartidor(entrega.getRepartidor());
                    entregaExistente.setRuta(entrega.getRuta());
                    entregaExistente.setFechaEntrega(entrega.getFechaEntrega());
                    entregaExistente.setEstado(entrega.getEstado());

                    return ResponseEntity.ok(
                            entregaService.guardar(entregaExistente)
                    );
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar entrega",
        description = "Elimina una entrega mediante su identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Entrega eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Entrega no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Identificador de la entrega", required = true)
            @PathVariable Long id) {

        return entregaService.buscarPorId(id)
                .map(entrega -> {
                    entregaService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}