package org.todoporunalma.api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.todoporunalma.api.domain.model.Participante;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

@Entity
@Table(name = "participantes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(unique = true, nullable = false)
    private String documento;
    
    @Column(nullable = false)
    private String nombres;
    
    @Column(nullable = false)
    private String apellidos;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    private String telefono;
    
    private String email;
    
    private String direccion;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Participante.EstadoParticipante estado = Participante.EstadoParticipante.ACTIVO;
    
    @Column(name = "sede_id")
    private UUID sedeId;
    
    @Column(name = "fecha_ingreso")
    @Builder.Default
    private LocalDate fechaIngreso = LocalDate.now();
    
    private String observaciones;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Computed field for age
    @Transient
    public Integer getEdad() {
        if (fechaNacimiento == null) {
            return null;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}
