package org.todoporunalma.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Archivo {
    private UUID id;
    private String nombre;
    private String ruta;
    private String url;
    private String mimeType;
    private Long tamaño;
    private String carpeta;
    private UUID participanteId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
