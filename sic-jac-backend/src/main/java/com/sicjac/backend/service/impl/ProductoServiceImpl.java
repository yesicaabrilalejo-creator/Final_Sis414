package com.sicjac.backend.service.impl;

import com.sicjac.backend.dto.ProductoDto;
import com.sicjac.backend.entity.Producto;
import com.sicjac.backend.exception.BadRequestException;
import com.sicjac.backend.exception.ResourceNotFoundException;
import com.sicjac.backend.repository.ProductoRepository;
import com.sicjac.backend.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDto> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDto buscarPorId(Long id) {
        return toDto(obtenerProducto(id));
    }

    @Override
    public ProductoDto crear(ProductoDto productoDto) {
        if (productoRepository.existsByCodigo(productoDto.getCodigo())) {
            throw new BadRequestException("Ya existe un producto con el codigo: " + productoDto.getCodigo());
        }
        Producto producto = toEntity(productoDto);
        producto.setId(null);
        return toDto(productoRepository.save(producto));
    }

    @Override
    public ProductoDto actualizar(Long id, ProductoDto productoDto) {
        Producto producto = obtenerProducto(id);
        if (!producto.getCodigo().equals(productoDto.getCodigo()) && productoRepository.existsByCodigo(productoDto.getCodigo())) {
            throw new BadRequestException("Ya existe un producto con el codigo: " + productoDto.getCodigo());
        }

        producto.setCodigo(productoDto.getCodigo());
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setPrecio(productoDto.getPrecio());
        producto.setStock(productoDto.getStock());
        return toDto(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProducto(id);
        productoRepository.delete(producto);
    }

    private Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    private ProductoDto toDto(Producto producto) {
        return ProductoDto.builder()
                .id(producto.getId())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .build();
    }

    private Producto toEntity(ProductoDto dto) {
        return Producto.builder()
                .id(dto.getId())
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .build();
    }
}
