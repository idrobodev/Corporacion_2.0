package org.todoporunalma.api.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.infrastructure.entity.ParticipanteEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaParticipanteRepository extends JpaRepository<ParticipanteEntity, String> {
    Optional<ParticipanteEntity> findByDocumento(String documento);
    List<ParticipanteEntity> findBySedeId(String sedeId);
    List<ParticipanteEntity> findByEstado(Participante.EstadoParticipante estado);
    boolean existsByDocumento(String documento);
    long countByEstado(Participante.EstadoParticipante estado);
}
