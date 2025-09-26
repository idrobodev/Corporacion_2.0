-- Insert initial sedes
INSERT INTO sedes (nombre, direccion, ciudad, telefono, tipo_sede, director_nombre, director_telefono) VALUES 
('Sede Masculina Bello', 'Bello, Antioquia', 'Bello', '3145702708', 'MASCULINA', 'Dr. Juan Camilo Machado', '3145702708'),
('Sede Femenina Bello', 'Bello, Antioquia', 'Bello', '3216481687', 'FEMENINA', 'Dra. Mildrey Leonel Melo', '3216481687'),
('Sede Masculina Apartadó', 'Apartadó, Antioquia', 'Apartadó', '3104577835', 'MASCULINA', 'Martín Muñoz Pino', '3104577835'),
('Sede Femenina Apartadó', 'Apartadó, Antioquia', 'Apartadó', '3104577835', 'FEMENINA', 'Dra. Luz Yasmin Estrada', '3104577835');

-- Insert initial admin user (password: admin123 - should be changed in production)
-- Password hash generated with BCrypt for 'admin123'
INSERT INTO usuarios (email, nombre, password_hash, rol) VALUES 
('admin@todoporunalma.org', 'Administrador', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'ADMINISTRADOR');

-- Insert a consultation user (password: consulta123)
-- Password hash generated with BCrypt for 'consulta123'
INSERT INTO usuarios (email, nombre, password_hash, rol) VALUES 
('consulta@todoporunalma.org', 'Usuario Consulta', '$2a$10$8K1p/a0dURdOFNfAOcXOOOxNfhkP5Le9Bb1GlVb5ttj.SA3/TS/jC', 'CONSULTA');

-- Insert some sample participants (optional - can be removed in production)
INSERT INTO participantes (documento, nombres, apellidos, fecha_nacimiento, telefono, email, direccion, sede_id, fecha_ingreso) 
SELECT 
    '12345678', 
    'Juan Carlos', 
    'Pérez García', 
    '1990-05-15', 
    '3001234567', 
    'juan.perez@email.com', 
    'Calle 123 #45-67, Bello', 
    s.id, 
    '2024-01-15'
FROM sedes s WHERE s.nombre = 'Sede Masculina Bello' LIMIT 1;

INSERT INTO participantes (documento, nombres, apellidos, fecha_nacimiento, telefono, email, direccion, sede_id, fecha_ingreso) 
SELECT 
    '87654321', 
    'María Elena', 
    'González López', 
    '1985-08-22', 
    '3009876543', 
    'maria.gonzalez@email.com', 
    'Carrera 45 #12-34, Bello', 
    s.id, 
    '2024-01-20'
FROM sedes s WHERE s.nombre = 'Sede Femenina Bello' LIMIT 1;
