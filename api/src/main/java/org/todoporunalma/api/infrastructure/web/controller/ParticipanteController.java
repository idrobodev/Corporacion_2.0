package org.todoporunalma.api.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.todoporunalma.api.application.service.ParticipanteService;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.infrastructure.web.dto.ApiResponse;
import org.todoporunalma.api.infrastructure.web.dto.ParticipanteDto;
import org.todoporunalma.api.infrastructure.web.mapper.ParticipanteDtoMapper;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/participantes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CONSULTA')")
@Tag(name = "Participantes", description = "Gestión de participantes del programa de rehabilitación")
public class ParticipanteController {

    private final ParticipanteService participanteService;
    private final ParticipanteDtoMapper participanteDtoMapper;

    @GetMapping
    @Operation(
        summary = "Listar todos los participantes",
        description = "Obtiene la lista completa de participantes registrados en el sistema",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lista de participantes obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ParticipanteDto.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "No autorizado - Token inválido o expirado"
        )
    })
    public ResponseEntity<ApiResponse<List<ParticipanteDto>>> getAllParticipantes() {
        try {
            var participantes = participanteService.findAll().stream()
                    .map(participanteDtoMapper::toDto)
                    .toList();
            return ResponseEntity.ok(ApiResponse.success(participantes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener participantes: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ParticipanteDto>> getParticipanteById(@PathVariable String id) {
        try {
            var participante = participanteService.findById(id)
                    .map(participanteDtoMapper::toDto)
                    .orElse(null);

            if (participante == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(ApiResponse.success(participante));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener participante: " + e.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<ParticipanteDto>> createParticipante(@Valid @RequestBody ParticipanteDto participanteDto) {
        try {
            if (participanteService.existsByDocumento(participanteDto.getDocumento())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ya existe un participante con este documento"));
            }

            var participante = participanteDtoMapper.toDomain(participanteDto);
            var savedParticipante = participanteService.save(participante);
            var responseDto = participanteDtoMapper.toDto(savedParticipante);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al crear participante: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<ParticipanteDto>> updateParticipante(
            @PathVariable String id,
            @Valid @RequestBody ParticipanteDto participanteDto) {
        try {
            if (!participanteService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            participanteDto.setId(id);
            var participante = participanteDtoMapper.toDomain(participanteDto);
            var updatedParticipante = participanteService.save(participante);
            var responseDto = participanteDtoMapper.toDto(updatedParticipante);

            return ResponseEntity.ok(ApiResponse.success(responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al actualizar participante: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<String>> deleteParticipante(@PathVariable String id) {
        try {
            if (!participanteService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            participanteService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Participante eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al eliminar participante: " + e.getMessage()));
        }
    }
}
