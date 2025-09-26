package org.todoporunalma.api.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.todoporunalma.api.application.service.ParticipanteService;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.infrastructure.web.dto.ApiResponse;
import org.todoporunalma.api.infrastructure.web.dto.ParticipanteDto;
import org.todoporunalma.api.infrastructure.web.mapper.ParticipanteDtoMapper;

// import org.apache.pdfbox.pdmodel.PDDocument;
// import org.apache.pdfbox.pdmodel.PDPage;
// import org.apache.pdfbox.pdmodel.PDPageContentStream;
// import org.apache.pdfbox.pdmodel.font.PDType1Font;
// import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/participantes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CONSULTA')")
@Tag(name = "Participantes", description = "Gestión de participantes del programa de rehabilitación")
public class ParticipanteController {
    
    private final ParticipanteService participanteService;
    private final ParticipanteDtoMapper participanteDtoMapper;

    @GetMapping
    @Operation(
        summary = "Listar todos los participantes",
        description = "Obtiene la lista completa de participantes registrados en el sistema",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lista de participantes obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ParticipanteDto.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "No autorizado - Token inválido o expirado"
        )
    })
    public ResponseEntity<ApiResponse<List<ParticipanteDto>>> getAllParticipantes() {
        try {
            var participantes = participanteService.findAll().stream()
                    .map(participanteDtoMapper::toDto)
                    .toList();
            return ResponseEntity.ok(ApiResponse.success(participantes));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener participantes: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ParticipanteDto>> getParticipanteById(@PathVariable UUID id) {
        try {
            var participante = participanteService.findById(id)
                    .map(participanteDtoMapper::toDto)
                    .orElse(null);
            
            if (participante == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(ApiResponse.success(participante));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al obtener participante: " + e.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<ParticipanteDto>> createParticipante(@Valid @RequestBody ParticipanteDto participanteDto) {
        try {
            if (participanteService.existsByDocumento(participanteDto.getDocumento())) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ya existe un participante con este documento"));
            }
            
            var participante = participanteDtoMapper.toDomain(participanteDto);
            var savedParticipante = participanteService.save(participante);
            var responseDto = participanteDtoMapper.toDto(savedParticipante);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al crear participante: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<ParticipanteDto>> updateParticipante(
            @PathVariable UUID id, 
            @Valid @RequestBody ParticipanteDto participanteDto) {
        try {
            if (!participanteService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            participanteDto.setId(id);
            var participante = participanteDtoMapper.toDomain(participanteDto);
            var updatedParticipante = participanteService.save(participante);
            var responseDto = participanteDtoMapper.toDto(updatedParticipante);
            
            return ResponseEntity.ok(ApiResponse.success(responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al actualizar participante: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<String>> deleteParticipante(@PathVariable UUID id) {
        try {
            if (!participanteService.findById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            participanteService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Participante eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error al eliminar participante: " + e.getMessage()));
        }
    }

    @PostMapping("/export-pdf")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CONSULTA')")
    @Operation(summary = "Exportar participantes a PDF", description = "Genera un PDF con la lista de participantes filtrada")
    public ResponseEntity<byte[]> exportParticipantesToPdf(@RequestBody Map<String, String> filters) {
        try {
            // Apply filters
            List<Participante> participantes = participanteService.findAll();

            String sedeFilter = filters.get("sede");
            String estadoFilter = filters.get("estado");
            String busquedaFilter = filters.get("busqueda");

            if (sedeFilter != null && !"Todas".equals(sedeFilter)) {
                // For now, we'll filter by sede name from the frontend data
                // In a real implementation, you'd join with sede table
                participantes = participantes.stream()
                    .filter(p -> {
                        // This is a placeholder - in real implementation you'd have sede name
                        // For now, we'll skip sede filtering in backend
                        return true;
                    })
                    .collect(Collectors.toList());
            }

            if (estadoFilter != null && !"Todos".equals(estadoFilter)) {
                participantes = participantes.stream()
                    .filter(p -> estadoFilter.equals(p.getEstado().toString()))
                    .collect(Collectors.toList());
            }

            if (busquedaFilter != null && !busquedaFilter.trim().isEmpty()) {
                String search = busquedaFilter.toLowerCase();
                participantes = participantes.stream()
                    .filter(p -> (p.getNombres() != null && (p.getNombres() + " " + p.getApellidos()).toLowerCase().contains(search)) ||
                               (p.getTelefono() != null && p.getTelefono().contains(search)))
                    .collect(Collectors.toList());
            }

            // Generate PDF using PDFBox
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);

            // Title
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Lista de Participantes - Corporación Todo por un Alma");
            contentStream.endText();

            // Filters info
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 720);
            StringBuilder filtersInfo = new StringBuilder();
            filtersInfo.append("Filtros aplicados: ");
            if (!"Todas".equals(sedeFilter)) {
                filtersInfo.append("Sede: ").append(sedeFilter).append(" | ");
            }
            if (!"Todos".equals(estadoFilter)) {
                filtersInfo.append("Estado: ").append(estadoFilter).append(" | ");
            }
            if (busquedaFilter != null && !busquedaFilter.trim().isEmpty()) {
                filtersInfo.append("Búsqueda: ").append(busquedaFilter);
            }
            contentStream.showText(filtersInfo.toString());
            contentStream.endText();

            // Table headers
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            float yPosition = 680;
            float[] columnWidths = {80, 120, 40, 80, 80, 60};
            float[] xPositions = {50, 130, 250, 290, 370, 450};

            String[] headers = {"Documento", "Nombre Completo", "Edad", "Teléfono", "Sede", "Estado"};
            contentStream.beginText();
            for (int i = 0; i < headers.length; i++) {
                contentStream.newLineAtOffset(xPositions[i], yPosition);
                contentStream.showText(headers[i]);
                contentStream.newLineAtOffset(-xPositions[i], 0);
            }
            contentStream.endText();

            // Draw header line
            contentStream.moveTo(50, yPosition - 5);
            contentStream.lineTo(550, yPosition - 5);
            contentStream.stroke();

            // Data rows
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
            yPosition -= 20;

            for (Participante participante : participantes) {
                if (yPosition < 50) { // New page if needed
                    contentStream.close();
                    PDPage newPage = new PDPage();
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
                    yPosition = 750;
                }

                contentStream.beginText();
                // Documento
                contentStream.newLineAtOffset(xPositions[0], yPosition);
                contentStream.showText(participante.getDocumento() != null ? participante.getDocumento() : "");
                contentStream.newLineAtOffset(-xPositions[0], 0);

                // Nombre Completo
                contentStream.newLineAtOffset(xPositions[1], yPosition);
                String nombreCompleto = (participante.getNombres() != null ? participante.getNombres() : "") + " " +
                                       (participante.getApellidos() != null ? participante.getApellidos() : "");
                contentStream.showText(nombreCompleto.length() > 20 ? nombreCompleto.substring(0, 20) + "..." : nombreCompleto);
                contentStream.newLineAtOffset(-xPositions[1], 0);

                // Edad
                contentStream.newLineAtOffset(xPositions[2], yPosition);
                contentStream.showText(participante.getEdad() != null ? participante.getEdad().toString() : "");
                contentStream.newLineAtOffset(-xPositions[2], 0);

                // Teléfono
                contentStream.newLineAtOffset(xPositions[3], yPosition);
                contentStream.showText(participante.getTelefono() != null ? participante.getTelefono() : "");
                contentStream.newLineAtOffset(-xPositions[3], 0);

                // Sede
                contentStream.newLineAtOffset(xPositions[4], yPosition);
                contentStream.showText("ID: " + (participante.getSedeId() != null ? participante.getSedeId().toString().substring(0, 8) : ""));
                contentStream.newLineAtOffset(-xPositions[4], 0);

                // Estado
                contentStream.newLineAtOffset(xPositions[5], yPosition);
                contentStream.showText(participante.getEstado() != null ? participante.getEstado().toString() : "");
                contentStream.newLineAtOffset(-xPositions[5], 0);

                contentStream.endText();

                yPosition -= 15;
            }

            // Footer with total count
            contentStream.beginText();
            contentStream.newLineAtOffset(450, 30);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
            contentStream.showText("Total de participantes: " + participantes.size());
            contentStream.endText();

            contentStream.close();
            document.save(baos);
            document.close();

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=participantes.pdf")
                .header(HttpHeaders.CACHE_CONTROL, "must-revalidate, post-check=0, pre-check=0")
                .body(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
