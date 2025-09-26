-- Script de inicialización de base de datos para desarrollo
-- Corporación Todo por un Alma

-- Crear directorios de uploads si no existen (esto se ejecuta desde el contenedor)
-- Nota: En producción, estos directorios deben crearse manualmente o por el sistema de archivos

-- Insertar datos de prueba para archivos
INSERT INTO archivos (nombre, ruta, url, mime_type, tamaño, carpeta, categoria, created_at, updated_at)
VALUES
('documento_ejemplo.pdf', '/app/uploads/documentos/documento_ejemplo.pdf', '/api/files/download/documentos/documento_ejemplo.pdf', 'application/pdf', 1024000, 'documentos', 'documentos', NOW(), NOW()),
('imagen_ejemplo.jpg', '/app/uploads/imagenes/imagen_ejemplo.jpg', '/api/files/download/imagenes/imagen_ejemplo.jpg', 'image/jpeg', 512000, 'imagenes', 'imagenes', NOW(), NOW()),
('video_ejemplo.mp4', '/app/uploads/videos/video_ejemplo.mp4', '/api/files/download/videos/video_ejemplo.mp4', 'video/mp4', 10485760, 'videos', 'videos', NOW(), NOW())
ON CONFLICT DO NOTHING;

-- Verificar estructura de archivos
SELECT
    '✅ Archivos por categoría:' as status,
    categoria,
    COUNT(*) as cantidad,
    pg_size_pretty(sum(tamaño)) as tamaño_total
FROM archivos
GROUP BY categoria
ORDER BY categoria;

-- Verificar permisos de archivos (si la tabla existe)
-- Nota: Esta consulta puede fallar si no hay archivos aún
SELECT
    '✅ Permisos de directorios:' as status,
    CASE
        WHEN pg_file_stat('/app/uploads/') IS NOT NULL THEN 'Directorios accesibles'
        ELSE 'Directorios no encontrados'
    END as estado_directorios;