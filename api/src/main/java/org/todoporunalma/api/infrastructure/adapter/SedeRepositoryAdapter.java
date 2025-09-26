package org.todoporunalma.api.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Sede;
import org.todoporunalma.api.domain.port.SedeRepository;
import org.todoporunalma.api.infrastructure.mapper.SedeMapper;
import org.todoporunalma.api.infrastructure.repository.JpaSedeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SedeRepositoryAdapter implements SedeRepository {
    
    private final JpaSedeRepository jpaSedeRepository;
    private final SedeMapper sedeMapper;

    @Override
    public Optional<Sede> findById(UUID id) {
        return jpaSedeRepository.findById(id)
                .map(sedeMapper::toDomain);
    }

    @Override
    public List<Sede> findAll() {
        return jpaSedeRepository.findAll().stream()
                .map(sedeMapper::toDomain)
                .toList();
    }

    @Override
    public List<Sede> findByEstado(Sede.EstadoSede estado) {
        return jpaSedeRepository.findByEstado(estado).stream()
                .map(sedeMapper::toDomain)
                .toList();
    }

    @Override
    public Sede save(Sede sede) {
        var entity = sedeMapper.toEntity(sede);
        var savedEntity = jpaSedeRepository.save(entity);
        return sedeMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaSedeRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return jpaSedeRepository.existsByNombre(nombre);
    }
}
