# Makefile para Corporación Todo por un Alma
# Proyecto completo con API Spring Boot + Frontend React + PostgreSQL

.PHONY: help build up down restart logs clean test dev prod backup restore health

# Variables
DOCKER_COMPOSE_FILE=docker-compose.yml
DOCKER_COMPOSE_DEV_FILE=docker-compose.dev.yml
PROJECT_NAME=corporacion-todo-por-un-alma

# Colores para output
RED=\033[0;31m
GREEN=\033[0;32m
YELLOW=\033[1;33m
BLUE=\033[0;34m
NC=\033[0m # No Color

# Help - Mostrar comandos disponibles
help:
	@echo "$(BLUE)🚀 $(PROJECT_NAME) - Comandos disponibles$(NC)"
	@echo ""
	@echo "$(YELLOW)Desarrollo:$(NC)"
	@echo "  make dev          - Iniciar entorno de desarrollo completo"
	@echo "  make dev-build    - Construir e iniciar desarrollo"
	@echo "  make dev-logs     - Ver logs del entorno de desarrollo"
	@echo "  make dev-down     - Detener entorno de desarrollo"
	@echo ""
	@echo "$(YELLOW)Producción:$(NC)"
	@echo "  make prod         - Iniciar entorno de producción"
	@echo "  make prod-build   - Construir e iniciar producción"
	@echo "  make prod-logs    - Ver logs de producción"
	@echo "  make prod-down    - Detener producción"
	@echo ""
	@echo "$(YELLOW)Base de Datos:$(NC)"
	@echo "  make db-init      - Inicializar base de datos"
	@echo "  make db-backup    - Crear backup de base de datos"
	@echo "  make db-restore   - Restaurar backup de base de datos"
	@echo "  make db-reset     - Resetear base de datos"
	@echo ""
	@echo "$(YELLOW)Testing:$(NC)"
	@echo "  make test         - Ejecutar todos los tests"
	@echo "  make test-api     - Tests de API"
	@echo "  make test-frontend- Tests de Frontend"
	@echo ""
	@echo "$(YELLOW)Utilidades:$(NC)"
	@echo "  make health       - Verificar estado de servicios"
	@echo "  make logs         - Ver logs de todos los servicios"
	@echo "  make clean        - Limpiar contenedores y volúmenes"
	@echo "  make update       - Actualizar dependencias"

# Desarrollo
dev:
	@echo "$(BLUE)🚀 Iniciando entorno de desarrollo...$(NC)"
	docker compose -f $(DOCKER_COMPOSE_DEV_FILE) up -d
	@echo "$(GREEN)✅ Entorno de desarrollo iniciado$(NC)"
	@echo "$(YELLOW)📱 Frontend: http://localhost$(NC)"
	@echo "$(YELLOW)🔧 API: http://localhost:8080/api$(NC)"
	@echo "$(YELLOW)📊 API Docs: http://localhost:8080/api/swagger-ui.html$(NC)"

dev-build:
	@echo "$(BLUE)🔨 Construyendo entorno de desarrollo...$(NC)"
	docker compose -f $(DOCKER_COMPOSE_DEV_FILE) up -d --build
	@echo "$(GREEN)✅ Entorno de desarrollo construido e iniciado$(NC)"

dev-logs:
	docker compose -f $(DOCKER_COMPOSE_DEV_FILE) logs -f

dev-down:
	@echo "$(YELLOW)🛑 Deteniendo entorno de desarrollo...$(NC)"
	docker compose -f $(DOCKER_COMPOSE_DEV_FILE) down
	@echo "$(GREEN)✅ Entorno de desarrollo detenido$(NC)"

# Producción
prod:
	@echo "$(BLUE)🚀 Iniciando entorno de producción...$(NC)"
	docker compose -f $(DOCKER_COMPOSE_FILE) up -d
	@echo "$(GREEN)✅ Entorno de producción iniciado$(NC)"

prod-build:
	@echo "$(BLUE)🔨 Construyendo entorno de producción...$(NC)"
	docker compose -f $(DOCKER_COMPOSE_FILE) up -d --build
	@echo "$(GREEN)✅ Entorno de producción construido e iniciado$(NC)"

prod-logs:
	docker compose -f $(DOCKER_COMPOSE_FILE) logs -f

prod-down:
	@echo "$(YELLOW)🛑 Deteniendo entorno de producción...$(NC)"
	docker compose -f $(DOCKER_COMPOSE_FILE) down
	@echo "$(GREEN)✅ Entorno de producción detenido$(NC)"

# Base de Datos
db-init:
	@echo "$(BLUE)🗄️ Inicializando base de datos...$(NC)"
	docker compose exec postgres psql -U todoporunalma_user -d todoporunalma_db -f /docker-entrypoint-initdb.d/01_database_setup.sql
	@echo "$(GREEN)✅ Base de datos inicializada$(NC)"

db-backup:
	@echo "$(BLUE)💾 Creando backup de base de datos...$(NC)"
	./backup.sh
	@echo "$(GREEN)✅ Backup creado$(NC)"

db-restore:
	@echo "$(YELLOW)⚠️  Esta acción sobrescribirá la base de datos actual$(NC)"
	@echo "$(YELLOW)¿Desea continuar? (y/N): $(NC)" && read ans && [ $${ans:-N} = y ]
	@echo "$(BLUE)🔄 Restaurando backup...$(NC)"
	@echo "$(RED)Función no implementada aún. Use manualmente:$(NC)"
	@echo "docker compose exec -T postgres psql -U todoporunalma_user -d todoporunalma_db < backups/database_backup_YYYYMMDD_HHMMSS.sql"

db-reset:
	@echo "$(RED)⚠️  Esta acción ELIMINARÁ TODOS LOS DATOS$(NC)"
	@echo "$(RED)¿Está completamente seguro? (yes/N): $(NC)" && read ans && [ $${ans:-N} = yes ]
	@echo "$(BLUE)🔄 Reseteando base de datos...$(NC)"
	docker compose down -v
	docker compose up -d postgres
	@echo "$(YELLOW)⏳ Esperando a que PostgreSQL inicie...$(NC)"
	sleep 10
	make db-init

# Testing
test:
	@echo "$(BLUE)🧪 Ejecutando todos los tests...$(NC)"
	@echo "$(YELLOW)Tests de API...$(NC)"
	make test-api
	@echo "$(YELLOW)Tests de Frontend...$(NC)"
	make test-frontend

test-api:
	@echo "$(BLUE)🔧 Ejecutando tests de API...$(NC)"
	cd api && ./mvnw test

test-frontend:
	@echo "$(BLUE)⚛️ Ejecutando tests de Frontend...$(NC)"
	cd coptua_react && npm test -- --watchAll=false --passWithNoTests

# Utilidades
health:
	@echo "$(BLUE)🏥 Verificando estado de servicios...$(NC)"
	@echo "$(YELLOW)Frontend:$(NC)"
	curl -s http://localhost/health || echo "❌ Frontend no disponible"
	@echo "$(YELLOW)API:$(NC)"
	curl -s http://localhost:8080/api/health || echo "❌ API no disponible"
	@echo "$(YELLOW)Base de datos:$(NC)"
	docker compose exec postgres pg_isready -U todoporunalma_user -d todoporunalma_db || echo "❌ Base de datos no disponible"

logs:
	docker compose logs -f

clean:
	@echo "$(RED)🧹 Limpiando contenedores, volúmenes e imágenes...$(NC)"
	docker compose down -v --rmi all
	docker system prune -f
	@echo "$(GREEN)✅ Limpieza completada$(NC)"

update:
	@echo "$(BLUE)📦 Actualizando dependencias...$(NC)"
	@echo "$(YELLOW)Actualizando API...$(NC)"
	cd api && ./mvnw versions:update-properties
	@echo "$(YELLOW)Actualizando Frontend...$(NC)"
	cd coptua_react && npm update
	@echo "$(GREEN)✅ Dependencias actualizadas$(NC)"

# Alias convenientes
build: prod-build
up: prod
down: prod-down
restart: prod-down prod

# Información del proyecto
info:
	@echo "$(BLUE)📋 Información del Proyecto$(NC)"
	@echo "Nombre: $(PROJECT_NAME)"
	@echo "Versión: 1.0.0"
	@echo "Frontend: React 17 + Tailwind CSS"
	@echo "Backend: Spring Boot 3.5.6 + Java 17"
	@echo "Base de datos: PostgreSQL 15"
	@echo ""
	@echo "$(YELLOW)URLs de desarrollo:$(NC)"
	@echo "  Frontend: http://localhost"
	@echo "  API: http://localhost:8080/api"
	@echo "  API Docs: http://localhost:8080/api/swagger-ui.html"
	@echo "  Base de datos: localhost:5432"

# Verificar prerrequisitos
check-deps:
	@echo "$(BLUE)🔍 Verificando dependencias del sistema...$(NC)"
	@command -v docker >/dev/null 2>&1 || { echo "$(RED)❌ Docker no está instalado$(NC)"; exit 1; }
	@command -v docker-compose >/dev/null 2>&1 || { echo "$(RED)❌ Docker Compose no está instalado$(NC)"; exit 1; }
	@echo "$(GREEN)✅ Todas las dependencias están instaladas$(NC)"

# Setup inicial del proyecto
setup:
	@echo "$(BLUE)⚙️ Configuración inicial del proyecto...$(NC)"
	make check-deps
	@echo "$(YELLOW)Copiando archivos de configuración...$(NC)"
	cp .env.example .env 2>/dev/null || echo "⚠️  .env.example no encontrado"
	@echo "$(GREEN)✅ Configuración completada$(NC)"
	@echo ""
	@echo "$(YELLOW)Para iniciar el desarrollo:$(NC)"
	@echo "  make dev-build"