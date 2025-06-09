package com.cobranza.notificaciones.repository;

import com.cobranza.notificaciones.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

