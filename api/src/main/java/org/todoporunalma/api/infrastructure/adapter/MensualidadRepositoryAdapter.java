package org.todoporunalma.api.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Mensualidad;
import org.todoporunalma.api.domain.port.MensualidadRepository;
import org.todoporunalma.api.infrastructure.mapper.MensualidadMapper;
import org.todoporunalma.api.infrastructure.repository.JpaMensualidadRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MensualidadRepositoryAdapter implements MensualidadRepository {
    
    private final JpaMensualidadRepository jpaMensualidadRepository;
    private final MensualidadMapper mensualidadMapper;

    @Override
    public Optional<Mensualidad> findById(UUID id) {
        return jpaMensualidadRepository.findById(id)
                .map(mensualidadMapper::toDomain);
    }

    @Override
    public List<Mensualidad> findAll() {
        return jpaMensualidadRepository.findAll().stream()
                .map(mensualidadMapper::toDomain)
                .toList();
    }

    @Override
    public List<Mensualidad> findByParticipanteId(UUID participanteId) {
        return jpaMensualidadRepository.findByParticipanteId(participanteId).stream()
                .map(mensualidadMapper::toDomain)
                .toList();
    }

    @Override
    public List<Mensualidad> findByEstado(Mensualidad.EstadoMensualidad estado) {
        return jpaMensualidadRepository.findByEstado(estado).stream()
                .map(mensualidadMapper::toDomain)
                .toList();
    }

    @Override
    public List<Mensualidad> findByMesAndAnio(Integer mes, Integer anio) {
        return jpaMensualidadRepository.findByMesAndAnio(mes, anio).stream()
                .map(mensualidadMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Mensualidad> findByParticipanteIdAndMesAndAnio(UUID participanteId, Integer mes, Integer anio) {
        return jpaMensualidadRepository.findByParticipanteIdAndMesAndAnio(participanteId, mes, anio)
                .map(mensualidadMapper::toDomain);
    }

    @Override
    public Mensualidad save(Mensualidad mensualidad) {
        var entity = mensualidadMapper.toEntity(mensualidad);
        var savedEntity = jpaMensualidadRepository.save(entity);
        return mensualidadMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaMensualidadRepository.deleteById(id);
    }

    @Override
    public long countByEstado(Mensualidad.EstadoMensualidad estado) {
        return jpaMensualidadRepository.countByEstado(estado);
    }
}
