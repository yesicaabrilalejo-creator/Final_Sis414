package com.sicjac.backend.service;

import com.sicjac.backend.dto.ProductoDto;

import java.util.List;

public interface ProductoService {

    List<ProductoDto> listarTodos();

    ProductoDto buscarPorId(Long id);

    ProductoDto crear(ProductoDto productoDto);

    ProductoDto actualizar(Long id, ProductoDto productoDto);

    void eliminar(Long id);
}
