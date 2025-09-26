package org.todoporunalma.api.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.todoporunalma.api.domain.model.Sede;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SedeDto {
    private UUID id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String ciudad;
    private String departamento;
    private Integer capacidadMaxima;
    private Sede.TipoSede tipoSede;
    private Sede.EstadoSede estado;
    private String directorNombre;
    private String directorTelefono;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
