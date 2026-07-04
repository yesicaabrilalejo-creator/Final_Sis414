package com.sicjac.backend.service.impl;

import com.sicjac.backend.dto.DistribuidorDto;
import com.sicjac.backend.entity.Distribuidor;
import com.sicjac.backend.entity.EstadoDistribuidor;
import com.sicjac.backend.entity.TipoDistribuidor;
import com.sicjac.backend.exception.BadRequestException;
import com.sicjac.backend.exception.ResourceNotFoundException;
import com.sicjac.backend.repository.DistribuidorRepository;
import com.sicjac.backend.service.DistribuidorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DistribuidorServiceImpl implements DistribuidorService {

    private final DistribuidorRepository distribuidorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DistribuidorDto> listarTodos() {
        return distribuidorRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DistribuidorDto buscarPorId(Long id) {
        return toDto(obtenerDistribuidor(id));
    }

    @Override
    public DistribuidorDto crear(DistribuidorDto distribuidorDto) {
        if (distribuidorRepository.existsByEmail(distribuidorDto.getEmail())) {
            throw new BadRequestException("Ya existe un distribuidor con el email: " + distribuidorDto.getEmail());
        }
        Distribuidor distribuidor = toEntity(distribuidorDto);
        distribuidor.setId(null);
        return toDto(distribuidorRepository.save(distribuidor));
    }

    @Override
    public DistribuidorDto actualizar(Long id, DistribuidorDto distribuidorDto) {
        Distribuidor distribuidor = obtenerDistribuidor(id);
        distribuidorRepository.findByEmail(distribuidorDto.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BadRequestException("Ya existe un distribuidor con el email: " + distribuidorDto.getEmail());
                });

        distribuidor.setNombre(distribuidorDto.getNombre());
        distribuidor.setRazonSocial(distribuidorDto.getRazonSocial());
        distribuidor.setContacto(distribuidorDto.getContacto());
        distribuidor.setDepartamento(distribuidorDto.getDepartamento());
        distribuidor.setCiudad(distribuidorDto.getCiudad());
        distribuidor.setDireccion(distribuidorDto.getDireccion());
        distribuidor.setTelefono(distribuidorDto.getTelefono());
        distribuidor.setCelular(distribuidorDto.getCelular());
        distribuidor.setWhatsapp(distribuidorDto.getWhatsapp());
        distribuidor.setEmail(distribuidorDto.getEmail());
        distribuidor.setFacebook(distribuidorDto.getFacebook());
        distribuidor.setTipo(distribuidorDto.getTipo());
        distribuidor.setEstado(distribuidorDto.getEstado());
        return toDto(distribuidorRepository.save(distribuidor));
    }

    @Override
    public void eliminar(Long id) {
        Distribuidor distribuidor = obtenerDistribuidor(id);
        distribuidorRepository.delete(distribuidor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DistribuidorDto> buscarPorDepartamento(String departamento) {
        return distribuidorRepository.findByDepartamentoIgnoreCase(departamento).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DistribuidorDto> buscarPorCiudad(String ciudad) {
        return distribuidorRepository.findByCiudadIgnoreCase(ciudad).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DistribuidorDto> buscarPorTipo(TipoDistribuidor tipo) {
        return distribuidorRepository.findByTipo(tipo).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DistribuidorDto> listarActivos() {
        return distribuidorRepository.findByEstado(EstadoDistribuidor.ACTIVO).stream()
                .map(this::toDto)
                .toList();
    }

    private Distribuidor obtenerDistribuidor(Long id) {
        return distribuidorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Distribuidor no encontrado con id: " + id));
    }

    private DistribuidorDto toDto(Distribuidor distribuidor) {
        return DistribuidorDto.builder()
                .id(distribuidor.getId())
                .nombre(distribuidor.getNombre())
                .razonSocial(distribuidor.getRazonSocial())
                .contacto(distribuidor.getContacto())
                .departamento(distribuidor.getDepartamento())
                .ciudad(distribuidor.getCiudad())
                .direccion(distribuidor.getDireccion())
                .telefono(distribuidor.getTelefono())
                .celular(distribuidor.getCelular())
                .whatsapp(distribuidor.getWhatsapp())
                .email(distribuidor.getEmail())
                .facebook(distribuidor.getFacebook())
                .tipo(distribuidor.getTipo())
                .estado(distribuidor.getEstado())
                .build();
    }

    private Distribuidor toEntity(DistribuidorDto dto) {
        return Distribuidor.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .razonSocial(dto.getRazonSocial())
                .contacto(dto.getContacto())
                .departamento(dto.getDepartamento())
                .ciudad(dto.getCiudad())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .celular(dto.getCelular())
                .whatsapp(dto.getWhatsapp())
                .email(dto.getEmail())
                .facebook(dto.getFacebook())
                .tipo(dto.getTipo())
                .estado(dto.getEstado())
                .build();
    }
}
