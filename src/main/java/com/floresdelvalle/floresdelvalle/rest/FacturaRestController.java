package com.floresdelvalle.floresdelvalle.rest;

import com.floresdelvalle.floresdelvalle.model.Factura;
import com.floresdelvalle.floresdelvalle.service.FacturaService;

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
@RequestMapping("/api/facturas")
@Tag(
    name = "Facturas",
    description = "Operaciones CRUD para la gestión de facturas"
)
public class FacturaRestController {

    private final FacturaService facturaService;

    public FacturaRestController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Operation(
        summary = "Listar todas las facturas",
        description = "Obtiene todas las facturas registradas."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Factura>> listarTodas() {
        return ResponseEntity.ok(facturaService.listarTodas());
    }

    @Operation(
        summary = "Consultar factura por ID",
        description = "Obtiene una factura específica mediante su identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Factura encontrada"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Factura> buscarPorId(
            @Parameter(description = "Identificador de la factura", required = true)
            @PathVariable Long id) {

        return facturaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear factura",
        description = "Registra una nueva factura."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Factura creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Factura> guardar(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.guardar(factura);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
    }

    @Operation(
        summary = "Actualizar factura",
        description = "Actualiza los datos de una factura existente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Factura actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizar(
            @Parameter(description = "Identificador de la factura", required = true)
            @PathVariable Long id,
            @RequestBody Factura factura) {

        return facturaService.buscarPorId(id)
                .map(facturaExistente -> {

                    facturaExistente.setPedido(factura.getPedido());
                    facturaExistente.setFecha(factura.getFecha());
                    facturaExistente.setPrecioFlores(factura.getPrecioFlores());
                    facturaExistente.setCostosAdicionales(factura.getCostosAdicionales());
                    facturaExistente.setTotal(factura.getTotal());
                    facturaExistente.setEstadoPago(factura.getEstadoPago());

                    return ResponseEntity.ok(
                            facturaService.guardar(facturaExistente)
                    );
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar factura",
        description = "Elimina una factura mediante su identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Factura eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Identificador de la factura", required = true)
            @PathVariable Long id) {

        return facturaService.buscarPorId(id)
                .map(factura -> {
                    facturaService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}