package com.diploma.backend.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
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
import com.diploma.backend.model.dto.TicketDTO;
import com.diploma.backend.model.dto.TicketSearchCriteria;
import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "externalId", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "searchCriteria", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", allowMultiple = true, dataType = "string", paramType = "query",
                    allowableValues = "Draft,Test,Pending Approval,Approved,Rejected,Archived,Scheduled,Active,"
                            + "Suspended,Aborted,Completed,Error"),
            @ApiImplicitParam(name = "segmentId", allowMultiple = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", allowMultiple = true, dataType = "string", paramType = "query",
                    allowableValues = "0,1"),
            @ApiImplicitParam(name = "ownerId", allowMultiple = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "entryCriterion", allowMultiple = true, dataType = "string", paramType = "query"
                    , allowableValues = "0,1"),
            @ApiImplicitParam(name = "dateStart", dataType = "string", paramType = "query", format = "date-time"),
            @ApiImplicitParam(name = "dateEnd", dataType = "string", paramType = "query", format = "date-time"),
            @ApiImplicitParam(name = "label", allowMultiple = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "channel", allowMultiple = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you "
                    + "want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records "
                    + "per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc).  Default sort order is ascending. "
                            + "Multiple sort criteria are supported. <br>" +
                            "<i>Available values:</i> name, dateStart, dateEnd, createdWhen, modifiedWhen, label <br>" +
                            "<i>Default value:</i> modifiedWhen,desc")})
    @GetMapping
    public List<TicketDTO> getAllTickets(TicketSearchCriteria search,
                                         @PageableDefault(size = 100, sort = Ticket.Fields.name,
                                                 direction = Sort.Direction.ASC) Pageable pageable) {
        Specification<Ticket> filters;
        Specification<Sort> sort;
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

}
