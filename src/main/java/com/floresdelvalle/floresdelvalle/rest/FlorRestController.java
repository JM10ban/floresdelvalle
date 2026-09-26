package com.floresdelvalle.floresdelvalle.rest;

import com.floresdelvalle.floresdelvalle.model.Flor;
import com.floresdelvalle.floresdelvalle.service.FlorService;

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
@RequestMapping("/api/flores")
@Tag(
    name = "Flores",
    description = "Operaciones CRUD para la gestión del inventario de flores"
)
public class FlorRestController {

    private final FlorService florService;

    public FlorRestController(FlorService florService) {
        this.florService = florService;
    }

    @Operation(
        summary = "Listar todas las flores",
        description = "Obtiene todas las flores registradas en el inventario."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de flores obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Flor>> listarTodas() {
        return ResponseEntity.ok(florService.listarTodas());
    }

    @Operation(
        summary = "Consultar una flor por ID",
        description = "Obtiene la información de una flor específica utilizando su identificador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flor encontrada correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró una flor con el ID indicado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Flor> buscarPorId(
            @Parameter(description = "Identificador de la flor", required = true)
            @PathVariable Long id) {

        return florService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Registrar una nueva flor",
        description = "Crea y almacena una nueva flor en el inventario."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Flor creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de la flor inválidos")
    })
    @PostMapping
    public ResponseEntity<Flor> guardar(@RequestBody Flor flor) {
        Flor nuevaFlor = florService.guardar(flor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFlor);
    }

    @Operation(
        summary = "Actualizar una flor",
        description = "Actualiza la información de una flor existente."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Flor actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró la flor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Flor> actualizar(
            @Parameter(description = "Identificador de la flor", required = true)
            @PathVariable Long id,
            @RequestBody Flor flor) {

        return florService.buscarPorId(id)
                .map(florExistente -> {

                    florExistente.setTipo(flor.getTipo());
                    florExistente.setColor(flor.getColor());
                    florExistente.setVariedad(flor.getVariedad());
                    florExistente.setCantidadDisponible(flor.getCantidadDisponible());
                    florExistente.setPrecioCompra(flor.getPrecioCompra());
                    florExistente.setPrecioVenta(flor.getPrecioVenta());

                    return ResponseEntity.ok(florService.guardar(florExistente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar una flor",
        description = "Elimina una flor del inventario utilizando su identificador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Flor eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró la flor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Identificador de la flor", required = true)
            @PathVariable Long id) {

        return florService.buscarPorId(id)
                .map(flor -> {
                    florService.eliminar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}