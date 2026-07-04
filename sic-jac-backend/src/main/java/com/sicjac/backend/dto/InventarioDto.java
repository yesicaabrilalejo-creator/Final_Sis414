package com.sicjac.backend.dto;

import com.sicjac.backend.entity.TipoMovimiento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDto {

    private Long id;

    @NotNull(message = "El productoId es obligatorio")
    private Long productoId;

    private String productoCodigo;

    private String productoNombre;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El tipoMovimiento es obligatorio")
    private TipoMovimiento tipoMovimiento;

    private LocalDateTime fechaMovimiento;
}
