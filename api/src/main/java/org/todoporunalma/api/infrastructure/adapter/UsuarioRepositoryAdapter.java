package org.todoporunalma.api.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Usuario;
import org.todoporunalma.api.domain.port.UsuarioRepository;
import org.todoporunalma.api.infrastructure.mapper.UsuarioMapper;
import org.todoporunalma.api.infrastructure.repository.JpaUsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepository {
    
    private final JpaUsuarioRepository jpaUsuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Optional<Usuario> findById(UUID id) {
        return jpaUsuarioRepository.findById(id)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return jpaUsuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaUsuarioRepository.findAll().stream()
                .map(usuarioMapper::toDomain)
                .toList();
    }

    @Override
    public Usuario save(Usuario usuario) {
        var entity = usuarioMapper.toEntity(usuario);
        var savedEntity = jpaUsuarioRepository.save(entity);
        return usuarioMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaUsuarioRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUsuarioRepository.existsByEmail(email);
    }
}
