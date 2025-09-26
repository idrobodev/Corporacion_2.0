package org.todoporunalma.api.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos de autenticación del usuario")
public class AuthRequest {
    @NotBlank(message = "Email es requerido")
    @Email(message = "Email debe tener un formato válido")
    @Schema(description = "Email del usuario", example = "admin@todoporunalma.org")
    private String email;
    
    @NotBlank(message = "Password es requerido")
    @Schema(description = "Contraseña del usuario", example = "admin123")
    private String password;
}
