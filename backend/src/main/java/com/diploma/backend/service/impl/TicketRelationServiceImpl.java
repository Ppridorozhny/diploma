package com.diploma.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diploma.backend.model.entities.TicketRelation;
import com.diploma.backend.repository.TicketRelationRepository;
import com.diploma.backend.service.TicketRelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketRelationServiceImpl implements TicketRelationService {

    private final TicketRelationRepository relationRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public TicketRelation create(TicketRelation relation) {
        return relationRepository.save(relation);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Integer id) {
        relationRepository.deleteById(id);
    }

    @Override
    public List<TicketRelation> getRelationsById(Integer id) {
        return relationRepository.getAllBySourceId(id);
    }

}
