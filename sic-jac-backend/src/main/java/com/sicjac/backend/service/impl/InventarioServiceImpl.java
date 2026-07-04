package com.sicjac.backend.service.impl;

import com.sicjac.backend.dto.InventarioDto;
import com.sicjac.backend.entity.Inventario;
import com.sicjac.backend.entity.Producto;
import com.sicjac.backend.exception.BadRequestException;
import com.sicjac.backend.exception.ResourceNotFoundException;
import com.sicjac.backend.repository.InventarioRepository;
import com.sicjac.backend.repository.ProductoRepository;
import com.sicjac.backend.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InventarioDto> listarTodos() {
        return inventarioRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InventarioDto buscarPorId(Long id) {
        return toDto(obtenerInventario(id));
    }

    @Override
    public InventarioDto crear(InventarioDto inventarioDto) {
        Producto producto = obtenerProducto(inventarioDto.getProductoId());
        Inventario inventario = toEntity(inventarioDto, producto);
        inventario.setId(null);
        if (inventario.getFechaMovimiento() == null) {
            inventario.setFechaMovimiento(LocalDateTime.now());
        }
        return toDto(inventarioRepository.save(inventario));
    }

    @Override
    public InventarioDto actualizar(Long id, InventarioDto inventarioDto) {
        Inventario inventario = obtenerInventario(id);
        Producto producto = obtenerProducto(inventarioDto.getProductoId());

        inventario.setProducto(producto);
        inventario.setCantidad(inventarioDto.getCantidad());
        inventario.setTipoMovimiento(inventarioDto.getTipoMovimiento());
        inventario.setFechaMovimiento(inventarioDto.getFechaMovimiento() != null
                ? inventarioDto.getFechaMovimiento()
                : inventario.getFechaMovimiento());
        return toDto(inventarioRepository.save(inventario));
    }

    @Override
    public void eliminar(Long id) {
        Inventario inventario = obtenerInventario(id);
        inventarioRepository.delete(inventario);
    }

    private Inventario obtenerInventario(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento de inventario no encontrado con id: " + id));
    }

    private Producto obtenerProducto(Long productoId) {
        if (productoId == null) {
            throw new BadRequestException("El productoId es obligatorio");
        }
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + productoId));
    }

    private InventarioDto toDto(Inventario inventario) {
        Producto producto = inventario.getProducto();
        return InventarioDto.builder()
                .id(inventario.getId())
                .productoId(producto.getId())
                .productoCodigo(producto.getCodigo())
                .productoNombre(producto.getNombre())
                .cantidad(inventario.getCantidad())
                .tipoMovimiento(inventario.getTipoMovimiento())
                .fechaMovimiento(inventario.getFechaMovimiento())
                .build();
    }

    private Inventario toEntity(InventarioDto dto, Producto producto) {
        return Inventario.builder()
                .id(dto.getId())
                .producto(producto)
                .cantidad(dto.getCantidad())
                .tipoMovimiento(dto.getTipoMovimiento())
                .fechaMovimiento(dto.getFechaMovimiento())
                .build();
    }
}
