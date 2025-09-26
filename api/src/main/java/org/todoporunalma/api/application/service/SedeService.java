package org.todoporunalma.api.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todoporunalma.api.domain.model.Sede;
import org.todoporunalma.api.domain.port.SedeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SedeService {
    
    private final SedeRepository sedeRepository;

    @Transactional(readOnly = true)
    public Optional<Sede> findById(UUID id) {
        return sedeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Sede> findAll() {
        return sedeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Sede> findByEstado(Sede.EstadoSede estado) {
        return sedeRepository.findByEstado(estado);
    }

    public Sede save(Sede sede) {
        if (sede.getId() == null) {
            sede.setId(UUID.randomUUID());
            sede.setCreatedAt(LocalDateTime.now());
        }
        sede.setUpdatedAt(LocalDateTime.now());
        return sedeRepository.save(sede);
    }

    public void deleteById(UUID id) {
        sedeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByNombre(String nombre) {
        return sedeRepository.existsByNombre(nombre);
    }
}
