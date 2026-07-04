package com.sicjac.backend.dto;

import com.sicjac.backend.entity.EstadoUsuario;
import com.sicjac.backend.entity.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UsuarioDto {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar 80 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 80, message = "El apellido no puede superar 80 caracteres")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato valido")
    @Size(max = 120, message = "El correo no puede superar 120 caracteres")
    private String correo;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 6, max = 120, message = "La password debe tener entre 6 y 120 caracteres")
    private String password;

    @Size(max = 30, message = "El telefono no puede superar 30 caracteres")
    private String telefono;

    @NotNull(message = "El estado es obligatorio")
    private EstadoUsuario estado;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;
}
