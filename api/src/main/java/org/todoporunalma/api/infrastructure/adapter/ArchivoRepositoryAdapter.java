package org.todoporunalma.api.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Archivo;
import org.todoporunalma.api.domain.port.ArchivoRepository;
import org.todoporunalma.api.infrastructure.mapper.ArchivoMapper;
import org.todoporunalma.api.infrastructure.repository.JpaArchivoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ArchivoRepositoryAdapter implements ArchivoRepository {
    
    private final JpaArchivoRepository jpaArchivoRepository;
    private final ArchivoMapper archivoMapper;

    @Override
    public Optional<Archivo> findById(UUID id) {
        return jpaArchivoRepository.findById(id)
                .map(archivoMapper::toDomain);
    }

    @Override
    public List<Archivo> findAll() {
        return jpaArchivoRepository.findAll().stream()
                .map(archivoMapper::toDomain)
                .toList();
    }

    @Override
    public List<Archivo> findByParticipanteId(UUID participanteId) {
        return jpaArchivoRepository.findByParticipanteId(participanteId).stream()
                .map(archivoMapper::toDomain)
                .toList();
    }

    @Override
    public List<Archivo> findByCarpeta(String carpeta) {
        return jpaArchivoRepository.findByCarpeta(carpeta).stream()
                .map(archivoMapper::toDomain)
                .toList();
    }

    @Override
    public Archivo save(Archivo archivo) {
        var entity = archivoMapper.toEntity(archivo);
        var savedEntity = jpaArchivoRepository.save(entity);
        return archivoMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaArchivoRepository.deleteById(id);
    }

    @Override
    public void deleteByRuta(String ruta) {
        jpaArchivoRepository.deleteByRuta(ruta);
    }
}
