package org.todoporunalma.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participante {
    private String id;
    private String documento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private Integer edad;
    private GeneroParticipante genero;
    private String telefono;
    private String email;
    private String direccion;
    private EstadoParticipante estado;
    private String sedeId;
    private LocalDate fechaIngreso;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum EstadoParticipante {
        ACTIVO, INACTIVO
    }

    public enum GeneroParticipante {
        MASCULINO, FEMENINO
    }
}
