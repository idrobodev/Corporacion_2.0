package org.todoporunalma.api.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todoporunalma.api.domain.model.Archivo;
import org.todoporunalma.api.domain.port.ArchivoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArchivoService {

    private final ArchivoRepository archivoRepository;

    @Transactional
    public Archivo save(Archivo archivo) {
        log.info("Guardando archivo: {}", archivo.getNombre());
        return archivoRepository.save(archivo);
    }

    @Transactional(readOnly = true)
    public Optional<Archivo> findById(UUID id) {
        return archivoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Archivo> findAll() {
        return archivoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Archivo> findByParticipanteId(UUID participanteId) {
        return archivoRepository.findByParticipanteId(participanteId);
    }

    @Transactional(readOnly = true)
    public List<Archivo> findByCarpeta(String carpeta) {
        return archivoRepository.findByCarpeta(carpeta);
    }

    @Transactional
    public void deleteById(UUID id) {
        log.info("Eliminando archivo con ID: {}", id);
        archivoRepository.deleteById(id);
    }

    @Transactional
    public void deleteByRuta(String ruta) {
        log.info("Eliminando archivo con ruta: {}", ruta);
        archivoRepository.deleteByRuta(ruta);
    }
}