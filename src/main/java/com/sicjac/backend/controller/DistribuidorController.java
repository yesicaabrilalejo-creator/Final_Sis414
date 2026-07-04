package com.sicjac.backend.controller;

import com.sicjac.backend.dto.DistribuidorDto;
import com.sicjac.backend.entity.TipoDistribuidor;
import com.sicjac.backend.service.DistribuidorService;
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

@Tag(name = "Distribuidores", description = "Gestion de distribuidores, sucursales y oficina central")
@RestController
@RequestMapping(AppConstants.API_BASE_PATH + "/distribuidores")
@RequiredArgsConstructor
public class DistribuidorController {

    private final DistribuidorService distribuidorService;

    @Operation(summary = "Listar distribuidores")
    @GetMapping
    public ResponseEntity<List<DistribuidorDto>> listarTodos() {
        return ResponseEntity.ok(distribuidorService.listarTodos());
    }

    @Operation(summary = "Buscar distribuidor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<DistribuidorDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(distribuidorService.buscarPorId(id));
    }

    @Operation(summary = "Crear distribuidor")
    @PostMapping
    public ResponseEntity<DistribuidorDto> crear(@Valid @RequestBody DistribuidorDto distribuidorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(distribuidorService.crear(distribuidorDto));
    }

    @Operation(summary = "Actualizar distribuidor")
    @PutMapping("/{id}")
    public ResponseEntity<DistribuidorDto> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody DistribuidorDto distribuidorDto) {
        return ResponseEntity.ok(distribuidorService.actualizar(id, distribuidorDto));
    }

    @Operation(summary = "Eliminar distribuidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        distribuidorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar distribuidores por departamento")
    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<DistribuidorDto>> buscarPorDepartamento(@PathVariable String departamento) {
        return ResponseEntity.ok(distribuidorService.buscarPorDepartamento(departamento));
    }

    @Operation(summary = "Listar distribuidores por ciudad")
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<DistribuidorDto>> buscarPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(distribuidorService.buscarPorCiudad(ciudad));
    }

    @Operation(summary = "Listar distribuidores por tipo")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<DistribuidorDto>> buscarPorTipo(@PathVariable TipoDistribuidor tipo) {
        return ResponseEntity.ok(distribuidorService.buscarPorTipo(tipo));
    }

    @Operation(summary = "Listar distribuidores activos")
    @GetMapping("/activos")
    public ResponseEntity<List<DistribuidorDto>> listarActivos() {
        return ResponseEntity.ok(distribuidorService.listarActivos());
    }
}
