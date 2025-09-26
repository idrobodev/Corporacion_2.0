package org.todoporunalma.api.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todoporunalma.api.domain.model.Mensualidad;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.domain.model.Sede;
import org.todoporunalma.api.domain.port.MensualidadRepository;
import org.todoporunalma.api.domain.port.ParticipanteRepository;
import org.todoporunalma.api.domain.port.SedeRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {
    
    private final ParticipanteRepository participanteRepository;
    private final MensualidadRepository mensualidadRepository;
    private final SedeRepository sedeRepository;

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("participantesActivos", participanteRepository.countByEstado(Participante.EstadoParticipante.ACTIVO));
        stats.put("mensualidadesPagadas", mensualidadRepository.countByEstado(Mensualidad.EstadoMensualidad.PAGADA));
        stats.put("mensualidadesPendientes", mensualidadRepository.countByEstado(Mensualidad.EstadoMensualidad.PENDIENTE));
        stats.put("sedesActivas", sedeRepository.findByEstado(Sede.EstadoSede.ACTIVA).size());
        
        return stats;
    }
}
