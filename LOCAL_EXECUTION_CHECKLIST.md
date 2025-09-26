# 🚀 CHECKLIST EJECUCIÓN PROYECTO EN LOCAL

## 📋 **PREPARACIÓN PREVIA**

### ✅ **1. Verificar Prerrequisitos del Sistema**
```bash
# Verificar Docker y Docker Compose
docker --version
docker-compose --version

# Verificar que Docker esté corriendo
docker info

# Verificar Git
git --version
```

### ✅ **2. Clonar y Configurar el Proyecto**
```bash
# Clonar el repositorio
git clone <repository-url>
cd corporacion-todo-por-un-alma

# Verificar archivos de configuración
ls -la
# Debe mostrar: .env.example, docker-compose.dev.yml, scripts/init-project.sh, etc.
```

### ✅ **3. Configurar Variables de Entorno**
```bash
# El script de inicialización crea automáticamente los archivos necesarios
./scripts/init-project.sh

# O manualmente:
# Copiar .env.example a .env
cp .env.example .env

# Crear .env.local para desarrollo frontend
cat > coptua_react/.env.local << EOF
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_SITE_URL=http://localhost:3001
EOF
```

## 🏗️ **EJECUCIÓN DEL PROYECTO**

### ✅ **4. Inicialización Completa (Recomendado)**
```bash
# Ejecutar inicialización completa
./scripts/init-project.sh all

# Esto hace automáticamente:
# - Verifica prerrequisitos
# - Configura variables de entorno
# - Construye imágenes Docker
# - Inicializa base de datos
```

### ✅ **5. Inicio del Entorno de Desarrollo**
```bash
# Opción 1: Usando Make (recomendado)
make dev-build

# Opción 2: Usando Docker Compose directamente
docker compose -f docker-compose.dev.yml up -d --build
```

### ✅ **6. Verificar Servicios**
```bash
# Verificar estado de contenedores
docker compose -f docker-compose.dev.yml ps

# Verificar logs
docker compose -f docker-compose.dev.yml logs -f

# Verificar conectividad
make health
```

## 🌐 **ACCESO A LA APLICACIÓN**

### ✅ **URLs de Desarrollo**
```
🌐 Frontend:    http://localhost:3001
🔧 API:         http://localhost:8080/api
📚 API Docs:    http://localhost:8080/api/swagger-ui.html
🗄️ Base Datos:  localhost:5432 (desde host)
🔧 Adminer:     http://localhost:8081 (usuario: todoporunalma_user)
```

### ✅ **Credenciales de Acceso**
```
👤 Administrador:
   Email: admin@todoporunalma.org
   Password: password

👤 Usuario Consulta:
   Email: consulta@todoporunalma.org
   Password: password
```

## 🔍 **VERIFICACIONES POST-EJECUCIÓN**

### ✅ **1. Verificar Base de Datos**
```bash
# Conectar a PostgreSQL
docker compose -f docker-compose.dev.yml exec postgres psql -U todoporunalma_user -d todoporunalma_db

# Verificar tablas creadas
\d

# Verificar datos iniciales
SELECT 'Fundación:' as tipo, nombre FROM fundacion;
SELECT 'Sedes:' as tipo, COUNT(*) FROM sedes;
SELECT 'Usuarios:' as tipo, COUNT(*) FROM usuarios;
SELECT 'Participantes:' as tipo, COUNT(*) FROM participantes;
```

### ✅ **2. Verificar API**
```bash
# Health check
curl http://localhost:8080/api/health

# Verificar endpoints principales
curl http://localhost:8080/api/sedes
curl http://localhost:8080/api/participantes
curl http://localhost:8080/api/dashboard/stats
```

### ✅ **3. Verificar Frontend**
```bash
# Verificar que el frontend esté sirviendo
curl -I http://localhost:3001

# Verificar configuración de API en frontend
curl http://localhost:3001 | grep -i "api"
```

## 🛠️ **COMANDOS ÚTILES PARA DESARROLLO**

### **Gestión de Servicios**
```bash
# Ver logs en tiempo real
make dev-logs

# Reiniciar servicios
make dev-down
make dev-build

# Limpiar todo
make clean
```

### **Base de Datos**
```bash
# Resetear base de datos
make db-reset

# Backup de base de datos
make db-backup

# Acceder a Adminer
open http://localhost:8081
```

### **Testing**
```bash
# Ejecutar tests
make test

# Tests específicos
make test-api
make test-frontend
```

## 🚨 **SOLUCIÓN DE PROBLEMAS**

### **Problema: Puerto ocupado**
```bash
# Ver qué proceso usa el puerto
lsof -i :3001
lsof -i :8080

# Matar proceso
kill -9 <PID>
```

### **Problema: Error de conexión a base de datos**
```bash
# Verificar que PostgreSQL esté corriendo
docker compose -f docker-compose.dev.yml ps postgres

# Reiniciar PostgreSQL
docker compose -f docker-compose.dev.yml restart postgres

# Ver logs de PostgreSQL
docker compose -f docker-compose.dev.yml logs postgres
```

### **Problema: Frontend no carga**
```bash
# Verificar logs del frontend
docker compose -f docker-compose.dev.yml logs frontend

# Reconstruir imagen del frontend
docker compose -f docker-compose.dev.yml build frontend
docker compose -f docker-compose.dev.yml up -d frontend
```

### **Problema: API no responde**
```bash
# Verificar logs de la API
docker compose -f docker-compose.dev.yml logs api

# Verificar variables de entorno
docker compose -f docker-compose.dev.yml exec api env | grep -E "(DB|JWT|CORS)"

# Reiniciar API
docker compose -f docker-compose.dev.yml restart api
```

## 📋 **VERIFICACIÓN FINAL**

### ✅ **Checklist de Verificación**
- [ ] Frontend accesible en http://localhost:3001
- [ ] API responde en http://localhost:8080/api/health
- [ ] Login funciona con credenciales de admin
- [ ] Dashboard carga correctamente
- [ ] Páginas de Participantes y Sedes funcionan
- [ ] Base de datos tiene datos iniciales
- [ ] API Docs accesible en Swagger UI

### ✅ **Funcionalidades a Probar**
- [ ] Login/Logout
- [ ] Navegación entre páginas
- [ ] CRUD de Participantes
- [ ] CRUD de Sedes
- [ ] Filtros y búsquedas
- [ ] Estadísticas en tiempo real
- [ ] Subida de archivos

## 🎉 **PROYECTO LISTO PARA DESARROLLO**

¡El proyecto está completamente configurado y listo para desarrollo local! 🚀

**Próximos pasos recomendados:**
1. Explorar la aplicación con las credenciales de administrador
2. Revisar la documentación en el README
3. Comenzar desarrollo de nuevas funcionalidades
4. Configurar tu IDE para desarrollo con hot-reload

**Recursos adicionales:**
- 📖 README.md - Documentación completa
- 🔧 Makefile - Comandos útiles
- 🐳 docker-compose.dev.yml - Configuración de desarrollo
- 📊 API Docs - Documentación de endpoints