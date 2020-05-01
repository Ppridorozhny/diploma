package com.diploma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diploma.backend.model.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    void deleteTicketById(Integer id);

    List<Ticket> getTicketsByProjectId(Integer projectId);

}
