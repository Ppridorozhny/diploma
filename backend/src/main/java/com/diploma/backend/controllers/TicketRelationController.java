package com.diploma.backend.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diploma.backend.AppConstants;
import com.diploma.backend.model.dto.TicketRelationDTO;
import com.diploma.backend.model.entities.TicketRelation;
import com.diploma.backend.service.TicketRelationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"TicketRelationController"})
@Validated
@RestController
@RequestMapping("/api/ticket-relations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TicketRelationController {

    private final TicketRelationService relationService;
    private final ConversionService conversionService;
    private final ModelMapper mapper;

    @GetMapping("/source/{sourceId}")
    public List<TicketRelationDTO> getTicketsByProjectId(@PathVariable Integer sourceId) {
        return mapper.map(relationService.getRelationsById(sourceId), AppConstants.RELATIONS_LIST_TYPE);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketRelationDTO create(@RequestBody @Validated TicketRelationDTO relationDTO) {
        TicketRelation relation = conversionService.convert(relationDTO, TicketRelation.class);
        relation = relationService.create(relation);
        return mapper.map(relation, TicketRelationDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        relationService.delete(id);
    }

}
