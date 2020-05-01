package com.diploma.backend.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diploma.backend.error.exceptions.ResourceNotFoundException;
import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.model.enums.TicketType;
import com.diploma.backend.repository.TicketRepository;
import com.diploma.backend.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
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
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteTicket(Integer id) {
        ticketRepository.deleteTicketById(id);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> getAllEpics() {
        return ticketRepository.getAllTicketByType(TicketType.EPIC);
    }

    @Override
    public List<Ticket> getTicketsByProjectId(Integer projectId) {
        return ticketRepository.getTicketsByProjectId(projectId);
    }

}
