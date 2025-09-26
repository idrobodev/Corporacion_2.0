package org.todoporunalma.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participante {
    private UUID id;
    private String documento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private Integer edad;
    private String telefono;
    private String email;
    private String direccion;
    private EstadoParticipante estado;
    private UUID sedeId;
    private LocalDate fechaIngreso;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum EstadoParticipante {
        ACTIVO, INACTIVO
    }
}
