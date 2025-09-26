package org.todoporunalma.api.domain.port;

import org.todoporunalma.api.domain.model.Participante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipanteRepository {
    Optional<Participante> findById(UUID id);
    Optional<Participante> findByDocumento(String documento);
    List<Participante> findAll();
    List<Participante> findBySedeId(UUID sedeId);
    List<Participante> findByEstado(Participante.EstadoParticipante estado);
    Participante save(Participante participante);
    void deleteById(UUID id);
    boolean existsByDocumento(String documento);
    long countByEstado(Participante.EstadoParticipante estado);
}
