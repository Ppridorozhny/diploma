package com.diploma.backend.controllers;

import com.diploma.backend.AppConstants;
import com.diploma.backend.model.dto.TicketDTO;
import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.service.TicketService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"TicketController"})
@Validated
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TicketController {

    private final TicketService ticketService;
    private final ModelMapper mapper;

    @GetMapping
    public List<TicketDTO> getAllTickets() {
        return mapper.map(ticketService.getAllTickets(), AppConstants.TICKET_LIST_TYPE);
    }

    @GetMapping("/{id}")
    public TicketDTO getTicketById(@PathVariable Integer id) {
        return mapper.map(ticketService.getTicket(id), TicketDTO.class);
    }

    @PostMapping
    public TicketDTO createTicket(@RequestBody @Validated TicketDTO ticketDTO) {
        Ticket ticket = mapper.map(ticketDTO, Ticket.class);
        ticket = ticketService.createTicket(ticket);
        return mapper.map(ticket, TicketDTO.class);
    }

    @PutMapping("/{id}")
    public TicketDTO updateTicket(@RequestBody @Validated TicketDTO ticketDTO, @PathVariable Integer id) {
        Ticket ticket = mapper.map(ticketDTO, Ticket.class);
        ticket = ticketService.updateTicket(id, ticket);
        return mapper.map(ticket, TicketDTO.class);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
    }

}
