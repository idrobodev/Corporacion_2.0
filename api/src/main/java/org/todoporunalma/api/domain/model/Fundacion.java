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
public class Fundacion {
    private UUID id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String email;
    private String website;
    private String mision;
    private String vision;
    private String valores;
    private String logoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
