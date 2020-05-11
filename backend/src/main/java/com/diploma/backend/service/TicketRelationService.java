package com.diploma.backend.service;

import java.util.List;

import com.diploma.backend.model.entities.TicketRelation;

public interface TicketRelationService {

    TicketRelation create(TicketRelation relation);

    void delete(Integer id);

    List<TicketRelation> getRelationsById(Integer id);

}
