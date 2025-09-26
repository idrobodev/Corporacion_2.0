package org.todoporunalma.api.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.todoporunalma.api.application.service.AuthService;
import org.todoporunalma.api.infrastructure.web.dto.ApiResponse;
import org.todoporunalma.api.infrastructure.web.dto.AuthRequest;
import org.todoporunalma.api.infrastructure.web.dto.AuthResponse;
import org.todoporunalma.api.infrastructure.web.dto.UsuarioDto;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para autenticación y gestión de sesiones")
public class AuthController {
    
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica un usuario y devuelve un token JWT para acceder a los endpoints protegidos",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciales de usuario",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthRequest.class),
                examples = @ExampleObject(
                    name = "Ejemplo de login",
                    summary = "Credenciales de administrador",
                    value = """
                        {
                          "email": "admin@todoporunalma.org",
                          "password": "admin123"
                        }
                        """
                )
            )
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Login exitoso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponse.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Credenciales inválidas",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "data": null,
                          "error": {
                            "message": "Credenciales inválidas"
                          }
                        }
                        """
                )
            )
        )
    })
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        try {
            var response = authService.login(request);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Credenciales inválidas"));
        }
    }

    @PostMapping("/logout")
    @Operation(
        summary = "Cerrar sesión",
        description = "Cierra la sesión del usuario. Con JWT, el logout se maneja del lado del cliente eliminando el token.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Logout exitoso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "data": "Logout exitoso",
                          "error": null
                        }
                        """
                )
            )
        )
    })
    public ResponseEntity<ApiResponse<String>> logout() {
        // With JWT, logout is handled client-side by removing the token
        return ResponseEntity.ok(ApiResponse.success("Logout exitoso"));
    }

    @GetMapping("/me")
    @Operation(
        summary = "Obtener usuario actual",
        description = "Obtiene la información del usuario autenticado actualmente",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Usuario obtenido exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioDto.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Usuario no encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "data": null,
                          "error": {
                            "message": "Usuario no encontrado"
                          }
                        }
                        """
                )
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "No autorizado - Token inválido o expirado"
        )
    })
    public ResponseEntity<ApiResponse<UsuarioDto>> getCurrentUser(Authentication authentication) {
        try {
            var user = authService.getCurrentUser(authentication.getName());
            return ResponseEntity.ok(ApiResponse.success(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Usuario no encontrado"));
        }
    }

    // ENDPOINT TEMPORAL PARA GENERAR HASH - ELIMINAR EN PRODUCCIÓN
    @GetMapping("/generate-hash/{password}")
    public ResponseEntity<ApiResponse<String>> generateHash(@PathVariable String password) {
        String hash = passwordEncoder.encode(password);
        return ResponseEntity.ok(ApiResponse.success(hash));
    }
}
