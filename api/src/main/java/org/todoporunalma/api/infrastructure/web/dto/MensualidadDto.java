package org.todoporunalma.api.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.todoporunalma.api.domain.model.Mensualidad;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensualidadDto {
    private UUID id;
    private UUID participanteId;
    private String participanteNombre; // For joined queries
    private String sedeNombre; // For joined queries
    private Integer mes;
    private Integer anio;
    private BigDecimal monto;
    private LocalDate fechaVencimiento;
    private LocalDate fechaPago;
    private Mensualidad.EstadoMensualidad estado;
    private String metodoPago;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
