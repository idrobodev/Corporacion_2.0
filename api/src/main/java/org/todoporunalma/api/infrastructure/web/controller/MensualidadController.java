package org.todoporunalma.api.infrastructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.todoporunalma.api.application.service.MensualidadService;
import org.todoporunalma.api.infrastructure.web.dto.ApiResponse;
import org.todoporunalma.api.infrastructure.web.dto.MensualidadDto;
import org.todoporunalma.api.infrastructure.web.mapper.MensualidadDtoMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mensualidades")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CONSULTA')")
public class MensualidadController {
    
    private final MensualidadService mensualidadService;
    private final MensualidadDtoMapper mensualidadDtoMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MensualidadDto>>> getAllMensualidades(
            @RequestParam(required = false) UUID participanteId) {
        try {
            List<MensualidadDto> mensualidades;
            
            if (participanteId != null) {
                mensualidades = mensualidadService.findByParticipanteId(participanteId).stream()
                        .map(mensualidadDtoMapper::toDto)
                        .toList();
            } else {
                mensualidades = mensualidadService.findAll().stream()
                        .map(mensualidadDtoMapper::toDto)
                        .toList();
            }
            
            return ResponseEntity.ok(ApiResponse.success(mensualidades));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener mensualidades: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MensualidadDto>> getMensualidadById(@PathVariable UUID id) {
        try {
            var mensualidad = mensualidadService.findById(id)
                    .map(mensualidadDtoMapper::toDto)
                    .orElse(null);
            
            if (mensualidad == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(ApiResponse.success(mensualidad));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener mensualidad: " + e.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<MensualidadDto>> createMensualidad(@Valid @RequestBody MensualidadDto mensualidadDto) {
        try {
            // Check if mensualidad already exists for this participant/month/year
            var existing = mensualidadService.findByParticipanteIdAndMesAndAnio(
                    mensualidadDto.getParticipanteId(), 
                    mensualidadDto.getMes(), 
                    mensualidadDto.getAnio());
            
            if (existing.isPresent()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ya existe una mensualidad para este participante en el mes/año especificado"));
            }
            
            var mensualidad = mensualidadDtoMapper.toDomain(mensualidadDto);
            var savedMensualidad = mensualidadService.save(mensualidad);
            var responseDto = mensualidadDtoMapper.toDto(savedMensualidad);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al crear mensualidad: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<MensualidadDto>> updateMensualidad(
            @PathVariable UUID id, 
            @Valid @RequestBody MensualidadDto mensualidadDto) {
        try {
            if (!mensualidadService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            mensualidadDto.setId(id);
            var mensualidad = mensualidadDtoMapper.toDomain(mensualidadDto);
            var updatedMensualidad = mensualidadService.save(mensualidad);
            var responseDto = mensualidadDtoMapper.toDto(updatedMensualidad);
            
            return ResponseEntity.ok(ApiResponse.success(responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al actualizar mensualidad: " + e.getMessage()));
        }
    }
}
