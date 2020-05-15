package com.diploma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.model.entities.UserStatistic;
import com.diploma.backend.model.enums.TicketType;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    void deleteTicketById(Integer id);

    List<Ticket> getTicketsByProjectId(Integer projectId);

    @Query("select epic from Ticket epic where epic.type = ?1")
    List<Ticket> getAllTicketByType(TicketType type);

    @Query(value = "select pu.username username, asssignee.count count from "
            + "("
            + "select pt.assignee_id, count(pt.assignee_id) count "
            + "from pr_ticket pt "
            + "where pt.project_id = :projectId "
            + "and pt.assignee_id notnull "
            + "group by pt.assignee_id "
            + ") asssignee "
            + "join pr_user pu "
            + "on asssignee.assignee_id = pu.user_id", nativeQuery = true)
    List<UserStatistic> getUserStatisticByProjectId(@Param("projectId") Integer projectId);

}
