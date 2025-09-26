package org.todoporunalma.api.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todoporunalma.api.infrastructure.entity.ArchivoEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaArchivoRepository extends JpaRepository<ArchivoEntity, UUID> {
    List<ArchivoEntity> findByParticipanteId(UUID participanteId);
    List<ArchivoEntity> findByCarpeta(String carpeta);
    void deleteByRuta(String ruta);
}
