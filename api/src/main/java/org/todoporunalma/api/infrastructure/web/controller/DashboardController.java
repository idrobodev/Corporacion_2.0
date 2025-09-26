package org.todoporunalma.api.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.todoporunalma.api.application.service.DashboardService;
import org.todoporunalma.api.infrastructure.web.dto.ApiResponse;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CONSULTA')")
@Tag(name = "Dashboard", description = "Estadísticas y métricas del sistema")
public class DashboardController {
    
    private final DashboardService dashboardService;

    @GetMapping("/stats")
    @Operation(
        summary = "Obtener estadísticas del dashboard",
        description = "Obtiene las estadísticas principales del sistema: participantes activos, mensualidades pagadas/pendientes y sedes activas",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Estadísticas obtenidas exitosamente",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "data": {
                            "participantesActivos": 45,
                            "mensualidadesPagadas": 120,
                            "mensualidadesPendientes": 15,
                            "sedesActivas": 4
                          },
                          "error": null
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
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        try {
            var stats = dashboardService.getStats();
            return ResponseEntity.ok(ApiResponse.success(stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener estadísticas: " + e.getMessage()));
        }
    }
}
