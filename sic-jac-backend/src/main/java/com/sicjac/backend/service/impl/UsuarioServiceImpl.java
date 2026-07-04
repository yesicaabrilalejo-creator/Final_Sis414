package com.sicjac.backend.service.impl;

import com.sicjac.backend.dto.UsuarioDto;
import com.sicjac.backend.entity.Usuario;
import com.sicjac.backend.exception.BadRequestException;
import com.sicjac.backend.exception.ResourceNotFoundException;
import com.sicjac.backend.repository.UsuarioRepository;
import com.sicjac.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto buscarPorId(Long id) {
        return toDto(obtenerUsuario(id));
    }

    @Override
    public UsuarioDto crear(UsuarioDto usuarioDto) {
        if (usuarioRepository.existsByCorreo(usuarioDto.getCorreo())) {
            throw new BadRequestException("Ya existe un usuario con el correo: " + usuarioDto.getCorreo());
        }
        Usuario usuario = toEntity(usuarioDto);
        usuario.setId(null);
        return toDto(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDto actualizar(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = obtenerUsuario(id);
        usuarioRepository.findByCorreo(usuarioDto.getCorreo())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BadRequestException("Ya existe un usuario con el correo: " + usuarioDto.getCorreo());
                });

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setCorreo(usuarioDto.getCorreo());
        usuario.setPassword(usuarioDto.getPassword());
        usuario.setTelefono(usuarioDto.getTelefono());
        usuario.setEstado(usuarioDto.getEstado());
        usuario.setRol(usuarioDto.getRol());
        return toDto(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = obtenerUsuario(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario obtenerUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    private UsuarioDto toDto(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .password(usuario.getPassword())
                .telefono(usuario.getTelefono())
                .estado(usuario.getEstado())
                .rol(usuario.getRol())
                .build();
    }

    private Usuario toEntity(UsuarioDto dto) {
        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .correo(dto.getCorreo())
                .password(dto.getPassword())
                .telefono(dto.getTelefono())
                .estado(dto.getEstado())
                .rol(dto.getRol())
                .build();
    }
}
