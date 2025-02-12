package com.isacgama.serviceorderapi.serviceorder;

import com.isacgama.serviceorderapi.cliente.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OSRepository extends JpaRepository<ServiceOrder, Long> {
    Page<ServiceOrder> findAllByAtivoTrue(Pageable paginacao);

    Page<ServiceOrder> findByClienteAndAtivoTrue(Cliente cliente, Pageable pageable);
}

