package com.sicjac.backend.service;

import com.sicjac.backend.dto.InventarioDto;

import java.util.List;

public interface InventarioService {

    List<InventarioDto> listarTodos();

    InventarioDto buscarPorId(Long id);

    InventarioDto crear(InventarioDto inventarioDto);

    InventarioDto actualizar(Long id, InventarioDto inventarioDto);

    void eliminar(Long id);
}
