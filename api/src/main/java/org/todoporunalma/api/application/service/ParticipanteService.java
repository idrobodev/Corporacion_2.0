package org.todoporunalma.api.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.domain.port.ParticipanteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipanteService {
    
    private final ParticipanteRepository participanteRepository;

    @Transactional(readOnly = true)
    public Optional<Participante> findById(UUID id) {
        return participanteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Participante> findByDocumento(String documento) {
        return participanteRepository.findByDocumento(documento);
    }

    @Transactional(readOnly = true)
    public List<Participante> findAll() {
        return participanteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Participante> findBySedeId(UUID sedeId) {
        return participanteRepository.findBySedeId(sedeId);
    }

    @Transactional(readOnly = true)
    public List<Participante> findByEstado(Participante.EstadoParticipante estado) {
        return participanteRepository.findByEstado(estado);
    }

    public Participante save(Participante participante) {
        if (participante.getId() == null) {
            participante.setId(UUID.randomUUID());
            participante.setCreatedAt(LocalDateTime.now());
        }
        participante.setUpdatedAt(LocalDateTime.now());
        return participanteRepository.save(participante);
    }

    public void deleteById(UUID id) {
        participanteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByDocumento(String documento) {
        return participanteRepository.existsByDocumento(documento);
    }

    @Transactional(readOnly = true)
    public long countByEstado(Participante.EstadoParticipante estado) {
        return participanteRepository.countByEstado(estado);
    }
}
