package com.diploma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diploma.backend.model.entities.TicketRelation;

public interface TicketRelationRepository extends JpaRepository<TicketRelation, Integer> {

    List<TicketRelation> getAllBySourceId(Integer id);

}
