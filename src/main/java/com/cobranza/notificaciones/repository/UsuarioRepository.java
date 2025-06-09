package com.cobranza.notificaciones.repository;

import com.cobranza.notificaciones.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

