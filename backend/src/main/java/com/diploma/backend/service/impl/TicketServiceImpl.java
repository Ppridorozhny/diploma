package com.diploma.backend.service.impl;

import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.repository.TicketRepository;
import com.diploma.backend.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket updateTicket(Integer id, Ticket ticket) {
        Ticket ticketFromCache = ticketRepository.getOne(id);
        BeanUtils.copyProperties(ticket, ticketFromCache, "id", "type", "reporter",
                "relations");
        return ticketRepository.save(ticketFromCache);
    }

    @Override
    public Ticket getTicket(Integer id) {
        return ticketRepository.getOne(id);
    }

    @Override
    public void deleteTicket(Integer id) {
        ticketRepository.deleteTicketById(id);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
