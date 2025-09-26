package org.todoporunalma.api.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.todoporunalma.api.infrastructure.entity.UsuarioEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
