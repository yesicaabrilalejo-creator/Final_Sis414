package com.sicjac.backend.service;

import com.sicjac.backend.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> listarTodos();

    UsuarioDto buscarPorId(Long id);

    UsuarioDto crear(UsuarioDto usuarioDto);

    UsuarioDto actualizar(Long id, UsuarioDto usuarioDto);

    void eliminar(Long id);
}
