package org.todoporunalma.api.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.todoporunalma.api.domain.model.Participante;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteDto {
    private UUID id;
    private String documento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private Integer edad;
    private String telefono;
    private String email;
    private String direccion;
    private Participante.EstadoParticipante estado;
    private UUID sedeId;
    private String sedeNombre; // For joined queries
    private LocalDate fechaIngreso;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
