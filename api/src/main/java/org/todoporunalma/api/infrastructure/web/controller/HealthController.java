package org.todoporunalma.api.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.todoporunalma.api.infrastructure.web.dto.ApiResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Tag(name = "Health", description = "Endpoint de salud del sistema")
public class HealthController {

    @GetMapping
    @Operation(
        summary = "Verificar estado del sistema",
        description = "Endpoint público para verificar que la API está funcionando correctamente"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Sistema funcionando correctamente",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "data": {
                            "status": "UP",
                            "timestamp": "2024-01-19T10:30:00",
                            "service": "Todo por un Alma API",
                            "version": "1.0.0"
                          },
                          "error": null
                        }
                        """
                )
            )
        )
    })
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "Todo por un Alma API");
        health.put("version", "1.0.0");
        
        return ResponseEntity.ok(ApiResponse.success(health));
    }
}
