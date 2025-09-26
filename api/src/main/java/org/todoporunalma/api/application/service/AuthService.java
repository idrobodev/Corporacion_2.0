package org.todoporunalma.api.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todoporunalma.api.domain.port.UsuarioRepository;
import org.todoporunalma.api.infrastructure.security.JwtService;
import org.todoporunalma.api.infrastructure.web.dto.AuthRequest;
import org.todoporunalma.api.infrastructure.web.dto.AuthResponse;
import org.todoporunalma.api.infrastructure.web.dto.UsuarioDto;
import org.todoporunalma.api.infrastructure.web.mapper.UsuarioDtoMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final UsuarioDtoMapper usuarioDtoMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Update last access
        usuarioService.updateLastAccess(usuario.getId());
        
        var userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtService.generateToken(userDetails);
        
        return AuthResponse.builder()
                .token(jwtToken)
                .user(usuarioDtoMapper.toDto(usuario))
                .build();
    }

    @Transactional(readOnly = true)
    public UsuarioDto getCurrentUser(String email) {
        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return usuarioDtoMapper.toDto(usuario);
    }
}
