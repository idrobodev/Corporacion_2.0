package org.todoporunalma.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mensualidad {
    private UUID id;
    private UUID participanteId;
    private Integer mes;
    private Integer anio;
    private BigDecimal monto;
    private LocalDate fechaVencimiento;
    private LocalDate fechaPago;
    private EstadoMensualidad estado;
    private String metodoPago;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum EstadoMensualidad {
        PENDIENTE, PAGADA, VENCIDA
    }
}
