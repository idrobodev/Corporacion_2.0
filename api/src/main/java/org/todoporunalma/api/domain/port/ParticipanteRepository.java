package org.todoporunalma.api.domain.port;

import org.todoporunalma.api.domain.model.Participante;

import java.util.List;
import java.util.Optional;

public interface ParticipanteRepository {
    Optional<Participante> findById(String id);
    Optional<Participante> findByDocumento(String documento);
    List<Participante> findAll();
    List<Participante> findBySedeId(String sedeId);
    List<Participante> findByEstado(Participante.EstadoParticipante estado);
    Participante save(Participante participante);
    void deleteById(String id);
    boolean existsByDocumento(String documento);
    long countByEstado(Participante.EstadoParticipante estado);
}
