package org.todoporunalma.api.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.todoporunalma.api.application.service.ArchivoService;
import org.todoporunalma.api.domain.model.Archivo;
import org.todoporunalma.api.infrastructure.web.dto.ApiResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Archivos", description = "Gestión de archivos y documentos")
public class FileController {

    private final ArchivoService archivoService;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    private Path getUploadPath() {
        return Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Subir archivo", description = "Sube un archivo al servidor")
    public ResponseEntity<ApiResponse<Map<String, Object>>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "path", defaultValue = "") String path) {
        
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("El archivo está vacío"));
            }

            // Validar tamaño (100MB máximo)
            if (file.getSize() > 100 * 1024 * 1024) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("El archivo excede el límite de 100MB"));
            }

            // Crear directorio si no existe
            Path uploadPath = getUploadPath();
            if (!path.isEmpty()) {
                uploadPath = uploadPath.resolve(path);
            }
            Files.createDirectories(uploadPath);

            // Generar nombre único para evitar conflictos
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

            // Guardar archivo
            Path targetLocation = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Crear registro en la base de datos
            Archivo archivo = Archivo.builder()
                .nombre(originalFilename)
                .ruta(targetLocation.toString())
                .url("/files/download/" + (path.isEmpty() ? "" : path + "/") + uniqueFilename)
                .mimeType(file.getContentType())
                .tamaño(file.getSize())
                .carpeta(path.isEmpty() ? null : path)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

            Archivo savedArchivo = archivoService.save(archivo);

            Map<String, Object> response = new HashMap<>();
            response.put("id", savedArchivo.getId());
            response.put("path", targetLocation.toString());
            response.put("filename", uniqueFilename);
            response.put("originalName", originalFilename);
            response.put("url", savedArchivo.getUrl());
            response.put("size", file.getSize());
            response.put("mimeType", file.getContentType());

            log.info("Archivo subido exitosamente: {} (ID: {})", uniqueFilename, savedArchivo.getId());
            return ResponseEntity.ok(ApiResponse.success(response));

        } catch (IOException e) {
            log.error("Error al subir archivo", e);
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error al subir archivo: " + e.getMessage()));
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CONSULTA')")
    @Operation(summary = "Listar archivos", description = "Lista archivos y carpetas en una ruta")
    public ResponseEntity<ApiResponse<Map<String, Object>>> listFiles(
            @RequestParam(value = "path", defaultValue = "") String path) {

        try {
            // Obtener archivos de la base de datos por carpeta
            List<Archivo> archivos = path.isEmpty() ?
                archivoService.findByCarpeta(null) :
                archivoService.findByCarpeta(path);

            // Obtener carpetas del sistema de archivos
            Path uploadPath = getUploadPath();
            if (!path.isEmpty()) {
                uploadPath = uploadPath.resolve(path);
            }

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            List<String> folders = Files.list(uploadPath)
                .filter(Files::isDirectory)
                .map(p -> p.getFileName().toString())
                .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("files", archivos);
            response.put("folders", folders);

            return ResponseEntity.ok(ApiResponse.success(response));

        } catch (IOException e) {
            log.error("Error al listar archivos", e);
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error al listar archivos: " + e.getMessage()));
        }
    }

    @GetMapping("/download/{filename}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'CONSULTA')")
    @Operation(summary = "Descargar archivo", description = "Descarga un archivo del servidor")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path filePath = getUploadPath().resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            log.error("Error al descargar archivo", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{filename}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Eliminar archivo", description = "Elimina un archivo del servidor")
    public ResponseEntity<ApiResponse<String>> deleteFile(@PathVariable String filename) {
        try {
            Path filePath = getUploadPath().resolve(filename).normalize();

            if (Files.exists(filePath)) {
                // Eliminar archivo del sistema de archivos
                Files.delete(filePath);

                // Eliminar registro de la base de datos
                String fullPath = filePath.toString();
                archivoService.deleteByRuta(fullPath);

                log.info("Archivo eliminado: {}", filename);
                return ResponseEntity.ok(ApiResponse.success("Archivo eliminado exitosamente"));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            log.error("Error al eliminar archivo", e);
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error al eliminar archivo: " + e.getMessage()));
        }
    }

    @PostMapping("/folder")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Crear carpeta", description = "Crea una nueva carpeta")
    public ResponseEntity<ApiResponse<String>> createFolder(
            @RequestBody Map<String, String> request) {
        
        try {
            String folderName = request.get("name");
            String parentPath = request.get("parentPath");
            
            if (folderName == null || folderName.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ApiResponse.error("El nombre de la carpeta es requerido"));
            }

            Path uploadPath = getUploadPath();
            if (parentPath != null && !parentPath.isEmpty()) {
                uploadPath = uploadPath.resolve(parentPath);
            }
            
            Path folderPath = uploadPath.resolve(folderName.trim());
            Files.createDirectories(folderPath);

            log.info("Carpeta creada: {}", folderPath);
            return ResponseEntity.ok(ApiResponse.success("Carpeta creada exitosamente"));

        } catch (IOException e) {
            log.error("Error al crear carpeta", e);
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error al crear carpeta: " + e.getMessage()));
        }
    }
}
