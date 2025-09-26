package org.todoporunalma.api.domain.port;

import org.todoporunalma.api.domain.model.Mensualidad;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MensualidadRepository {
    Optional<Mensualidad> findById(UUID id);
    List<Mensualidad> findAll();
    List<Mensualidad> findByParticipanteId(UUID participanteId);
    List<Mensualidad> findByEstado(Mensualidad.EstadoMensualidad estado);
    List<Mensualidad> findByMesAndAnio(Integer mes, Integer anio);
    Optional<Mensualidad> findByParticipanteIdAndMesAndAnio(UUID participanteId, Integer mes, Integer anio);
    Mensualidad save(Mensualidad mensualidad);
    void deleteById(UUID id);
    long countByEstado(Mensualidad.EstadoMensualidad estado);
}
