package org.todoporunalma.api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.todoporunalma.api.domain.model.Sede;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sedes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SedeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String direccion;
    
    private String telefono;
    
    private String email;
    
    @Column(nullable = false)
    private String ciudad;
    
    @Builder.Default
    private String departamento = "Antioquia";
    
    @Column(name = "capacidad_maxima")
    @Builder.Default
    private Integer capacidadMaxima = 30;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sede")
    @Builder.Default
    private Sede.TipoSede tipoSede = Sede.TipoSede.MIXTA;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Sede.EstadoSede estado = Sede.EstadoSede.ACTIVA;
    
    @Column(name = "director_nombre")
    private String directorNombre;
    
    @Column(name = "director_telefono")
    private String directorTelefono;
    
    private String observaciones;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
