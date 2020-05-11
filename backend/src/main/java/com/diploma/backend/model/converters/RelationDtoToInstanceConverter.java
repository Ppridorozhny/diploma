package com.diploma.backend.model.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.diploma.backend.model.dto.TicketRelationDTO;
import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.model.entities.TicketRelation;
import com.diploma.backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RelationDtoToInstanceConverter implements Converter<TicketRelationDTO, TicketRelation> {

    private final TicketRepository ticketRepository;
    private final ModelMapper mapper;

    @Override
    public TicketRelation convert(TicketRelationDTO relationDTO) {
        TicketRelation relation = mapper.map(relationDTO, TicketRelation.class);

        Ticket source = ticketRepository.getOne(relationDTO.getSource().getId());
        Ticket target = ticketRepository.getOne(relationDTO.getTarget().getId());

        relation.setSource(source);
        relation.setTarget(target);

        return relation;
    }

}
