package org.todoporunalma.api.infrastructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.todoporunalma.api.application.service.SedeService;
import org.todoporunalma.api.infrastructure.web.dto.ApiResponse;
import org.todoporunalma.api.infrastructure.web.dto.SedeDto;
import org.todoporunalma.api.infrastructure.web.mapper.SedeDtoMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sedes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CONSULTA')")
public class SedeController {
    
    private final SedeService sedeService;
    private final SedeDtoMapper sedeDtoMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SedeDto>>> getAllSedes() {
        try {
            var sedes = sedeService.findAll().stream()
                    .map(sedeDtoMapper::toDto)
                    .toList();
            return ResponseEntity.ok(ApiResponse.success(sedes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener sedes: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SedeDto>> getSedeById(@PathVariable UUID id) {
        try {
            var sede = sedeService.findById(id)
                    .map(sedeDtoMapper::toDto)
                    .orElse(null);
            
            if (sede == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(ApiResponse.success(sede));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener sede: " + e.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<SedeDto>> createSede(@Valid @RequestBody SedeDto sedeDto) {
        try {
            if (sedeService.existsByNombre(sedeDto.getNombre())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ya existe una sede con este nombre"));
            }
            
            var sede = sedeDtoMapper.toDomain(sedeDto);
            var savedSede = sedeService.save(sede);
            var responseDto = sedeDtoMapper.toDto(savedSede);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al crear sede: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<SedeDto>> updateSede(
            @PathVariable UUID id, 
            @Valid @RequestBody SedeDto sedeDto) {
        try {
            if (!sedeService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            sedeDto.setId(id);
            var sede = sedeDtoMapper.toDomain(sedeDto);
            var updatedSede = sedeService.save(sede);
            var responseDto = sedeDtoMapper.toDto(updatedSede);
            
            return ResponseEntity.ok(ApiResponse.success(responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al actualizar sede: " + e.getMessage()));
        }
    }
}
