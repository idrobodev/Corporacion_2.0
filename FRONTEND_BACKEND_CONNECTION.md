# Frontend-Backend Connection Configuration

## Issue Identified

The frontend application is configured to connect to the production API (`https://api.todoporunalma.org/api`) in the `.env` file, but for local development, it should connect to the local Spring Boot API running on `http://localhost:8080/api`.

## Current Configuration

### Frontend (.env) - Production
```
REACT_APP_API_BASE_URL=https://api.todoporunalma.org/api
```

### Frontend (.env.local) - Development Override
```
REACT_APP_API_BASE_URL=http://localhost:8080/api
```

### Frontend (package.json) - Development Proxy
```json
"proxy": "http://localhost:8080/api"
```

### API Configuration (application.properties)
```
server.port=8080
server.servlet.context-path=/api
```

## Solution

Create a `.env.local` file in the `coptua_react` directory with the local development configuration:

```bash
# Development Configuration
REACT_APP_API_BASE_URL=http://localhost:8080/api
```

### Manual Steps:
1. Navigate to the `coptua_react` directory
2. Create a new file named `.env.local`
3. Add the content above
4. Restart the React development server

This file is automatically ignored by Git and will override the production settings in `.env`.

This file will override the production settings when running in development mode.

## CORS Configuration

The backend is properly configured with CORS:

### application.properties
```
app.cors.allowed-origins=http://localhost,http://localhost:3000,http://localhost:3001,http://localhost:80,http://127.0.0.1:3000,http://127.0.0.1:80,https://todoporunalma.org
app.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
app.cors.allowed-headers=*
app.cors.allow-credentials=true
```

### SecurityConfig.java
- CORS is properly configured with the properties from application.properties
- JWT authentication filter is in place
- Public endpoints are correctly configured

## Testing Connection

To test the connection:

1. Start the Spring Boot API on port 8080
2. Start the React app on port 3001 (as configured in package.json)
3. Check browser console for API calls
4. Verify that authentication and data fetching work correctly

## API Endpoints

The frontend expects these endpoints to be available:

- `POST /api/auth/login` - User authentication
- `GET /api/participantes` - Get participants
- `PUT /api/participantes/{id}` - Update participant
- `POST /api/participantes` - Create participant
- `GET /api/dashboard/stats` - Dashboard statistics
- `GET /api/health` - Health check

## Authentication Flow

1. User logs in via `/api/auth/login`
2. JWT token is stored in localStorage
3. Subsequent requests include `Authorization: Bearer {token}` header
4. 401 responses trigger redirect to login page

## Environment Variables Priority

React loads environment variables in this order:
1. `.env.local` (highest priority, ignored by git)
2. `.env.development` or `.env.production`
3. `.env` (lowest priority)

For local development, use `.env.local` to override production settings.