package org.todoporunalma.api.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "archivos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false, length = 500)
    private String ruta;
    
    @Column(length = 500)
    private String url;
    
    @Column(name = "mime_type", length = 100)
    private String mimeType;
    
    @Column(name = "tamaño")
    private Long tamaño;
    
    private String carpeta;
    
    @Column(name = "participante_id")
    private UUID participanteId;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
