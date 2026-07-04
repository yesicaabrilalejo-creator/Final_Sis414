package com.sicjac.backend.repository;

import com.sicjac.backend.entity.Distribuidor;
import com.sicjac.backend.entity.EstadoDistribuidor;
import com.sicjac.backend.entity.TipoDistribuidor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistribuidorRepository extends JpaRepository<Distribuidor, Long> {

    boolean existsByEmail(String email);

    Optional<Distribuidor> findByEmail(String email);

    List<Distribuidor> findByDepartamentoIgnoreCase(String departamento);

    List<Distribuidor> findByCiudadIgnoreCase(String ciudad);

    List<Distribuidor> findByTipo(TipoDistribuidor tipo);

    List<Distribuidor> findByEstado(EstadoDistribuidor estado);
}
