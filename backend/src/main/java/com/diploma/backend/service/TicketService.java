package com.diploma.backend.service;

import com.diploma.backend.model.entities.Ticket;

import java.util.List;

public interface TicketService {
    Ticket createTicket(Ticket ticket);

    Ticket updateTicket(Integer id, Ticket ticket);

    Ticket getTicket(Integer id);

    void deleteTicket(Integer id);

    List<Ticket> getAllTickets();
}
