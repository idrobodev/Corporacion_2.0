package org.todoporunalma.api.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.domain.port.ParticipanteRepository;
import org.todoporunalma.api.infrastructure.mapper.ParticipanteMapper;
import org.todoporunalma.api.infrastructure.repository.JpaParticipanteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ParticipanteRepositoryAdapter implements ParticipanteRepository {
    
    private final JpaParticipanteRepository jpaParticipanteRepository;
    private final ParticipanteMapper participanteMapper;

    @Override
    public Optional<Participante> findById(UUID id) {
        return jpaParticipanteRepository.findById(id)
                .map(participanteMapper::toDomain);
    }

    @Override
    public Optional<Participante> findByDocumento(String documento) {
        return jpaParticipanteRepository.findByDocumento(documento)
                .map(participanteMapper::toDomain);
    }

    @Override
    public List<Participante> findAll() {
        return jpaParticipanteRepository.findAll().stream()
                .map(participanteMapper::toDomain)
                .toList();
    }

    @Override
    public List<Participante> findBySedeId(UUID sedeId) {
        return jpaParticipanteRepository.findBySedeId(sedeId).stream()
                .map(participanteMapper::toDomain)
                .toList();
    }

    @Override
    public List<Participante> findByEstado(Participante.EstadoParticipante estado) {
        return jpaParticipanteRepository.findByEstado(estado).stream()
                .map(participanteMapper::toDomain)
                .toList();
    }

    @Override
    public Participante save(Participante participante) {
        var entity = participanteMapper.toEntity(participante);
        var savedEntity = jpaParticipanteRepository.save(entity);
        return participanteMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaParticipanteRepository.deleteById(id);
    }

    @Override
    public boolean existsByDocumento(String documento) {
        return jpaParticipanteRepository.existsByDocumento(documento);
    }

    @Override
    public long countByEstado(Participante.EstadoParticipante estado) {
        return jpaParticipanteRepository.countByEstado(estado);
    }
}
