-- Create extension for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create function for updating updated_at timestamps
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ language 'plpgsql';

-- USUARIOS table
CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(255) UNIQUE NOT NULL,
    nombre VARCHAR(255),
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) DEFAULT 'CONSULTA' CHECK (rol IN ('CONSULTA', 'ADMINISTRADOR')),
    activo BOOLEAN DEFAULT true,
    ultimo_acceso TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- SEDES table
CREATE TABLE sedes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    ciudad VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL DEFAULT 'Antioquia',
    capacidad_maxima INTEGER DEFAULT 30,
    tipo_sede VARCHAR(20) DEFAULT 'MIXTA' CHECK (tipo_sede IN ('MASCULINA', 'FEMENINA', 'MIXTA')),
    estado VARCHAR(20) DEFAULT 'ACTIVA' CHECK (estado IN ('ACTIVA', 'INACTIVA', 'MANTENIMIENTO')),
    director_nombre VARCHAR(255),
    director_telefono VARCHAR(20),
    observaciones TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- PARTICIPANTES table
CREATE TABLE participantes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    documento VARCHAR(20) UNIQUE NOT NULL,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE,
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion TEXT,
    estado VARCHAR(20) DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO', 'INACTIVO')),
    sede_id UUID REFERENCES sedes(id),
    fecha_ingreso DATE DEFAULT CURRENT_DATE,
    observaciones TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- MENSUALIDADES table
CREATE TABLE mensualidades (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    participante_id UUID REFERENCES participantes(id) ON DELETE CASCADE,
    mes INTEGER NOT NULL CHECK (mes BETWEEN 1 AND 12),
    anio INTEGER NOT NULL,
    monto DECIMAL(10,2) NOT NULL DEFAULT 0,
    fecha_vencimiento DATE,
    fecha_pago DATE,
    estado VARCHAR(20) DEFAULT 'PENDIENTE' CHECK (estado IN ('PENDIENTE', 'PAGADA', 'VENCIDA')),
    metodo_pago VARCHAR(50),
    observaciones TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    UNIQUE(participante_id, mes, anio)
);

-- ARCHIVOS table
CREATE TABLE archivos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre VARCHAR(255) NOT NULL,
    ruta VARCHAR(500) NOT NULL,
    url VARCHAR(500),
    mime_type VARCHAR(100),
    tamaño BIGINT,
    carpeta VARCHAR(255),
    participante_id UUID REFERENCES participantes(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create indexes for better performance
CREATE INDEX idx_participantes_sede ON participantes(sede_id);
CREATE INDEX idx_participantes_estado ON participantes(estado);
CREATE INDEX idx_participantes_documento ON participantes(documento);
CREATE INDEX idx_mensualidades_participante ON mensualidades(participante_id);
CREATE INDEX idx_mensualidades_fecha ON mensualidades(mes, anio);
CREATE INDEX idx_mensualidades_estado ON mensualidades(estado);
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_archivos_participante ON archivos(participante_id);

-- Create triggers for updated_at columns
CREATE TRIGGER update_usuarios_updated_at BEFORE UPDATE ON usuarios 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_sedes_updated_at BEFORE UPDATE ON sedes 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_participantes_updated_at BEFORE UPDATE ON participantes 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_mensualidades_updated_at BEFORE UPDATE ON mensualidades 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_archivos_updated_at BEFORE UPDATE ON archivos 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
