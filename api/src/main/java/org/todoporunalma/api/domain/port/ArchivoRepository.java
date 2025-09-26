package org.todoporunalma.api.domain.port;

import org.todoporunalma.api.domain.model.Archivo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArchivoRepository {
    Optional<Archivo> findById(UUID id);
    List<Archivo> findAll();
    List<Archivo> findByParticipanteId(UUID participanteId);
    List<Archivo> findByCarpeta(String carpeta);
    Archivo save(Archivo archivo);
    void deleteById(UUID id);
    void deleteByRuta(String ruta);
}
