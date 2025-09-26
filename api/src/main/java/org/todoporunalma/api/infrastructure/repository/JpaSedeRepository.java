package org.todoporunalma.api.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todoporunalma.api.domain.model.Sede;
import org.todoporunalma.api.infrastructure.entity.SedeEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaSedeRepository extends JpaRepository<SedeEntity, UUID> {
    List<SedeEntity> findByEstado(Sede.EstadoSede estado);
    boolean existsByNombre(String nombre);
}
