#!/bin/bash

# Script de inicialización del proyecto
# Corporación Todo por un Alma

set -e

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función de logging
log() {
    echo -e "${BLUE}[$(date +'%Y-%m-%d %H:%M:%S')] $1${NC}"
}

success() {
    echo -e "${GREEN}✅ $1${NC}"
}

warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

error() {
    echo -e "${RED}❌ $1${NC}"
}

# Verificar prerrequisitos
check_prerequisites() {
    log "Verificando prerrequisitos del sistema..."

    # Verificar Docker
    if ! command -v docker &> /dev/null; then
        error "Docker no está instalado. Por favor instala Docker primero."
        exit 1
    fi

    # Verificar Docker Compose
    if ! command -v docker-compose &> /dev/null && ! docker compose version &> /dev/null; then
        error "Docker Compose no está instalado. Por favor instala Docker Compose primero."
        exit 1
    fi

    # Verificar Git
    if ! command -v git &> /dev/null; then
        error "Git no está instalado. Por favor instala Git primero."
        exit 1
    fi

    success "Prerrequisitos verificados correctamente"
}

# Configurar variables de entorno
setup_environment() {
    log "Configurando variables de entorno..."

    # Copiar archivo de ejemplo si no existe .env
    if [ ! -f ".env" ]; then
        if [ -f ".env.example" ]; then
            cp .env.example .env
            warning "Se copió .env.example a .env. Revisa y configura las variables según tu entorno."
        else
            warning "No se encontró .env.example. Creando .env básico..."
            cat > .env << EOF
# Configuración básica para desarrollo local
POSTGRES_DB=todoporunalma_db
POSTGRES_USER=todoporunalma_user
POSTGRES_PASSWORD=todoporunalma_pass
JWT_SECRET=todoporunalma-jwt-secret-key-2024-very-long-and-secure-key-for-development
JWT_EXPIRATION=86400000
JWT_REFRESH_EXPIRATION=604800000
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:3001,http://localhost:80
SPRING_PROFILE=development
EOF
        fi
    else
        success "Archivo .env ya existe"
    fi

    # Configurar .env.local para frontend si no existe
    if [ ! -f "coptua_react/.env.local" ]; then
        cat > coptua_react/.env.local << EOF
# Configuración de desarrollo para React
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_SITE_URL=http://localhost:3001
EOF
        success "Archivo .env.local creado para desarrollo frontend"
    fi
}

# Construir imágenes Docker
build_images() {
    log "Construyendo imágenes Docker..."

    if [ "$1" = "dev" ]; then
        log "Construyendo imágenes de desarrollo..."
        docker compose -f docker-compose.dev.yml build
    else
        log "Construyendo imágenes de producción..."
        docker compose build
    fi

    success "Imágenes Docker construidas correctamente"
}

# Inicializar base de datos
init_database() {
    log "Inicializando base de datos..."

    # Iniciar solo PostgreSQL
    docker compose up -d postgres

    # Esperar a que PostgreSQL esté listo
    log "Esperando a que PostgreSQL inicie..."
    sleep 10

    # Verificar conexión
    if docker compose exec -T postgres pg_isready -U todoporunalma_user -d todoporunalma_db; then
        success "PostgreSQL está listo"
    else
        error "PostgreSQL no está respondiendo"
        exit 1
    fi

    # Ejecutar script de inicialización
    log "Ejecutando script de inicialización de base de datos..."
    docker compose exec -T postgres psql -U todoporunalma_user -d todoporunalma_db -f /docker-entrypoint-initdb.d/01_database_setup.sql

    success "Base de datos inicializada correctamente"
}

# Verificar servicios
check_services() {
    log "Verificando servicios..."

    # Verificar API
    if curl -f http://localhost:8080/api/health &> /dev/null; then
        success "API está funcionando correctamente"
    else
        warning "API no está respondiendo aún (puede estar iniciando)"
    fi

    # Verificar Frontend (si está corriendo)
    if curl -f http://localhost:3001/health &> /dev/null; then
        success "Frontend está funcionando correctamente"
    else
        warning "Frontend no está disponible (inicia con 'make dev' si es necesario)"
    fi
}

# Función principal
main() {
    echo -e "${BLUE}🚀 Inicialización del Proyecto - Corporación Todo por un Alma${NC}"
    echo ""

    case "${1:-all}" in
        "check")
            check_prerequisites
            ;;
        "env")
            setup_environment
            ;;
        "build")
            check_prerequisites
            setup_environment
            build_images "$2"
            ;;
        "db")
            check_prerequisites
            init_database
            ;;
        "verify")
            check_services
            ;;
        "all")
            check_prerequisites
            setup_environment
            build_images "dev"
            init_database
            echo ""
            success "Proyecto inicializado correctamente!"
            echo ""
            echo -e "${YELLOW}Para iniciar el desarrollo:${NC}"
            echo "  make dev"
            echo ""
            echo -e "${YELLOW}URLs de acceso:${NC}"
            echo "  Frontend: http://localhost:3001"
            echo "  API: http://localhost:8080/api"
            echo "  API Docs: http://localhost:8080/api/swagger-ui.html"
            ;;
        *)
            echo "Uso: $0 {check|env|build|db|verify|all} [dev|prod]"
            echo ""
            echo "Comandos:"
            echo "  check  - Verificar prerrequisitos"
            echo "  env    - Configurar variables de entorno"
            echo "  build  - Construir imágenes Docker"
            echo "  db     - Inicializar base de datos"
            echo "  verify - Verificar servicios"
            echo "  all    - Ejecutar todo el proceso de inicialización"
            exit 1
            ;;
    esac
}

# Ejecutar función principal
main "$@"