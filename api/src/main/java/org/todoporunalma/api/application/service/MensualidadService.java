package org.todoporunalma.api.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todoporunalma.api.domain.model.Mensualidad;
import org.todoporunalma.api.domain.port.MensualidadRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MensualidadService {
    
    private final MensualidadRepository mensualidadRepository;

    @Transactional(readOnly = true)
    public Optional<Mensualidad> findById(UUID id) {
        return mensualidadRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Mensualidad> findAll() {
        return mensualidadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Mensualidad> findByParticipanteId(UUID participanteId) {
        return mensualidadRepository.findByParticipanteId(participanteId);
    }

    @Transactional(readOnly = true)
    public List<Mensualidad> findByEstado(Mensualidad.EstadoMensualidad estado) {
        return mensualidadRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Mensualidad> findByMesAndAnio(Integer mes, Integer anio) {
        return mensualidadRepository.findByMesAndAnio(mes, anio);
    }

    @Transactional(readOnly = true)
    public Optional<Mensualidad> findByParticipanteIdAndMesAndAnio(UUID participanteId, Integer mes, Integer anio) {
        return mensualidadRepository.findByParticipanteIdAndMesAndAnio(participanteId, mes, anio);
    }

    public Mensualidad save(Mensualidad mensualidad) {
        if (mensualidad.getId() == null) {
            mensualidad.setId(UUID.randomUUID());
            mensualidad.setCreatedAt(LocalDateTime.now());
        }
        mensualidad.setUpdatedAt(LocalDateTime.now());
        return mensualidadRepository.save(mensualidad);
    }

    public void deleteById(UUID id) {
        mensualidadRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countByEstado(Mensualidad.EstadoMensualidad estado) {
        return mensualidadRepository.countByEstado(estado);
    }
}
