package com.sicjac.backend.dto;

import com.sicjac.backend.entity.EstadoDistribuidor;
import com.sicjac.backend.entity.TipoDistribuidor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistribuidorDto {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    private String nombre;

    @NotBlank(message = "La razon social es obligatoria")
    @Size(max = 160, message = "La razon social no puede superar 160 caracteres")
    private String razonSocial;

    @NotBlank(message = "El contacto es obligatorio")
    @Size(max = 120, message = "El contacto no puede superar 120 caracteres")
    private String contacto;

    @NotBlank(message = "El departamento es obligatorio")
    @Size(max = 80, message = "El departamento no puede superar 80 caracteres")
    private String departamento;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 80, message = "La ciudad no puede superar 80 caracteres")
    private String ciudad;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 250, message = "La direccion no puede superar 250 caracteres")
    private String direccion;

    @Size(max = 30, message = "El telefono no puede superar 30 caracteres")
    @Pattern(regexp = "^[0-9+()\\-\\s]*$", message = "El telefono solo puede contener numeros, espacios, +, - y parentesis")
    private String telefono;

    @Size(max = 30, message = "El celular no puede superar 30 caracteres")
    @Pattern(regexp = "^[0-9+()\\-\\s]*$", message = "El celular solo puede contener numeros, espacios, +, - y parentesis")
    private String celular;

    @Size(max = 30, message = "El whatsapp no puede superar 30 caracteres")
    @Pattern(regexp = "^[0-9+()\\-\\s]*$", message = "El whatsapp solo puede contener numeros, espacios, +, - y parentesis")
    private String whatsapp;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    @Size(max = 120, message = "El email no puede superar 120 caracteres")
    private String email;

    @Size(max = 250, message = "El facebook no puede superar 250 caracteres")
    private String facebook;

    @NotNull(message = "El tipo es obligatorio")
    private TipoDistribuidor tipo;

    @NotNull(message = "El estado es obligatorio")
    private EstadoDistribuidor estado;
}
