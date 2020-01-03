package com.diploma.backend.repository;

import com.diploma.backend.model.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    void deleteTicketById(Integer id);
}
