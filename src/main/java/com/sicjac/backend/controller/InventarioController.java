package com.sicjac.backend.controller;

import com.sicjac.backend.dto.InventarioDto;
import com.sicjac.backend.service.InventarioService;
import com.sicjac.backend.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Inventario", description = "Gestion de movimientos de inventario")
@RestController
@RequestMapping(AppConstants.API_BASE_PATH + "/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @Operation(summary = "Listar movimientos de inventario")
    @GetMapping
    public ResponseEntity<List<InventarioDto>> listarTodos() {
        return ResponseEntity.ok(inventarioService.listarTodos());
    }

    @Operation(summary = "Buscar movimiento de inventario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<InventarioDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.buscarPorId(id));
    }

    @Operation(summary = "Crear movimiento de inventario")
    @PostMapping
    public ResponseEntity<InventarioDto> crear(@Valid @RequestBody InventarioDto inventarioDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.crear(inventarioDto));
    }

    @Operation(summary = "Actualizar movimiento de inventario")
    @PutMapping("/{id}")
    public ResponseEntity<InventarioDto> actualizar(@PathVariable Long id,
                                                    @Valid @RequestBody InventarioDto inventarioDto) {
        return ResponseEntity.ok(inventarioService.actualizar(id, inventarioDto));
    }

    @Operation(summary = "Eliminar movimiento de inventario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
