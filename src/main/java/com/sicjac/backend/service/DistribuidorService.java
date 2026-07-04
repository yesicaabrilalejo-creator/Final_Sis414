package com.sicjac.backend.service;

import com.sicjac.backend.dto.DistribuidorDto;
import com.sicjac.backend.entity.TipoDistribuidor;

import java.util.List;

public interface DistribuidorService {

    List<DistribuidorDto> listarTodos();

    DistribuidorDto buscarPorId(Long id);

    DistribuidorDto crear(DistribuidorDto distribuidorDto);

    DistribuidorDto actualizar(Long id, DistribuidorDto distribuidorDto);

    void eliminar(Long id);

    List<DistribuidorDto> buscarPorDepartamento(String departamento);

    List<DistribuidorDto> buscarPorCiudad(String ciudad);

    List<DistribuidorDto> buscarPorTipo(TipoDistribuidor tipo);

    List<DistribuidorDto> listarActivos();
}
