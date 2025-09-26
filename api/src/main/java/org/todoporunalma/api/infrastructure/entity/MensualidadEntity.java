package org.todoporunalma.api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.todoporunalma.api.domain.model.Mensualidad;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mensualidades", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"participante_id", "mes", "anio"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MensualidadEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "participante_id", nullable = false)
    private UUID participanteId;
    
    @Column(nullable = false)
    private Integer mes;
    
    @Column(nullable = false)
    private Integer anio;
    
    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal monto = BigDecimal.ZERO;
    
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column(name = "fecha_pago")
    private LocalDate fechaPago;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Mensualidad.EstadoMensualidad estado = Mensualidad.EstadoMensualidad.PENDIENTE;
    
    @Column(name = "metodo_pago")
    private String metodoPago;
    
    private String observaciones;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
