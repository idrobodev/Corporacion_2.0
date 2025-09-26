package org.todoporunalma.api.domain.port;

import org.todoporunalma.api.domain.model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    void deleteById(UUID id);
    boolean existsByEmail(String email);
}
