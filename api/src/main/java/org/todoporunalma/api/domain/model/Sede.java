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
public class Sede {
    private UUID id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String ciudad;
    private String departamento;
    private Integer capacidadMaxima;
    private TipoSede tipoSede;
    private EstadoSede estado;
    private String directorNombre;
    private String directorTelefono;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum TipoSede {
        MASCULINA, FEMENINA, MIXTA
    }

    public enum EstadoSede {
        ACTIVA, INACTIVA, MANTENIMIENTO
    }
}
