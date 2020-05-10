package com.diploma.backend.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diploma.backend.AppConstants;
import com.diploma.backend.model.dto.ChangeStatusDTO;
import com.diploma.backend.model.dto.TicketDTO;
import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.model.pojo.ChangeStatus;
import com.diploma.backend.service.TicketService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"TicketController"})
@Validated
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TicketController {

    private final TicketService ticketService;
    private final ModelMapper mapper;

    @GetMapping
    public List<TicketDTO> getAllTickets() {
        return mapper.map(ticketService.getAllTickets(), AppConstants.TICKET_LIST_TYPE);
    }

    @GetMapping("/epics")
    public List<TicketDTO> getAllEpics() {
        return mapper.map(ticketService.getAllEpics(), AppConstants.TICKET_LIST_TYPE);
    }

    @GetMapping("project/{projectId}")
    public List<TicketDTO> getTicketsByProjectId(@PathVariable Integer projectId) {
        return mapper.map(ticketService.getTicketsByProjectId(projectId), AppConstants.TICKET_LIST_TYPE);
    }

    @GetMapping("/{id}")
    public TicketDTO getTicketById(@PathVariable Integer id) {
        return mapper.map(ticketService.getTicket(id), TicketDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
    }

    @PostMapping("/change-status")
    public TicketDTO changeStatus(@RequestBody @Validated ChangeStatusDTO changeStatusDTO) {
        ChangeStatus changeStatus = mapper.map(changeStatusDTO, ChangeStatus.class);
        Ticket updatedTicket = ticketService.changeStatus(changeStatus);
        return mapper.map(updatedTicket, TicketDTO.class);
    }

}
