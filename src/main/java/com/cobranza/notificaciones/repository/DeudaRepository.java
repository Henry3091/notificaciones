package com.cobranza.notificaciones.repository;

import com.cobranza.notificaciones.model.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeudaRepository extends JpaRepository<Deuda, Long> {
}

