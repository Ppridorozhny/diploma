package com.diploma.backend.repository;

import com.diploma.backend.model.entities.TicketRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRelationRepository extends JpaRepository<TicketRelation, Integer> {
}
