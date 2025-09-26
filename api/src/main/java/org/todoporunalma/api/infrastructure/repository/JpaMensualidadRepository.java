package org.todoporunalma.api.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todoporunalma.api.domain.model.Mensualidad;
import org.todoporunalma.api.infrastructure.entity.MensualidadEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaMensualidadRepository extends JpaRepository<MensualidadEntity, UUID> {
    List<MensualidadEntity> findByParticipanteId(UUID participanteId);
    List<MensualidadEntity> findByEstado(Mensualidad.EstadoMensualidad estado);
    List<MensualidadEntity> findByMesAndAnio(Integer mes, Integer anio);
    Optional<MensualidadEntity> findByParticipanteIdAndMesAndAnio(UUID participanteId, Integer mes, Integer anio);
    long countByEstado(Mensualidad.EstadoMensualidad estado);
}
