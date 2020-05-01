package com.diploma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.model.enums.TicketType;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    void deleteTicketById(Integer id);

    List<Ticket> getTicketsByProjectId(Integer projectId);

    @Query("select epic from Ticket epic where epic.type = ?1")
    List<Ticket> getAllTicketByType(TicketType type);

}
