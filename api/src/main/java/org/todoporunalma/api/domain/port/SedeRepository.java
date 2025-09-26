package org.todoporunalma.api.domain.port;

import org.todoporunalma.api.domain.model.Sede;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SedeRepository {
    Optional<Sede> findById(UUID id);
    List<Sede> findAll();
    List<Sede> findByEstado(Sede.EstadoSede estado);
    Sede save(Sede sede);
    void deleteById(UUID id);
    boolean existsByNombre(String nombre);
}
