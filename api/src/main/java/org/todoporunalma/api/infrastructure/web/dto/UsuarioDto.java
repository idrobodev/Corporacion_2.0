package org.todoporunalma.api.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.todoporunalma.api.domain.model.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del usuario del sistema")
public class UsuarioDto {
    @Schema(description = "ID único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;
    
    @Schema(description = "Email del usuario", example = "admin@todoporunalma.org")
    private String email;
    
    @Schema(description = "Nombre completo del usuario", example = "Administrador")
    private String nombre;
    
    @Schema(description = "Rol del usuario en el sistema", example = "ADMINISTRADOR")
    private Usuario.Rol rol;
    
    @Schema(description = "Estado activo del usuario", example = "true")
    private Boolean activo;
    
    @Schema(description = "Fecha y hora del último acceso")
    private LocalDateTime ultimoAcceso;
    
    @Schema(description = "Fecha y hora de creación")
    private LocalDateTime createdAt;
    
    @Schema(description = "Fecha y hora de última actualización")
    private LocalDateTime updatedAt;
}
