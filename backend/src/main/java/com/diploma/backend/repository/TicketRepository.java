package com.diploma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.diploma.backend.model.entities.StatisticEntry;
import com.diploma.backend.model.entities.Ticket;
import com.diploma.backend.model.entities.TicketTypeStatisticEntry;
import com.diploma.backend.model.enums.TicketType;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    void deleteTicketById(Integer id);

    List<Ticket> getTicketsByProjectId(Integer projectId);

    @Query("select epic from Ticket epic where epic.type = ?1")
    List<Ticket> getAllTicketByType(TicketType type);

    @Query(value = "select pu.username as name, asssignee.count count from "
            + "("
            + "select pt.assignee_id, count(pt.assignee_id) count "
            + "from pr_ticket pt "
            + "where pt.project_id = :projectId "
            + "and pt.assignee_id notnull "
            + "group by pt.assignee_id "
            + ") asssignee "
            + "join pr_user pu "
            + "on asssignee.assignee_id = pu.user_id", nativeQuery = true)
    List<StatisticEntry> getUserStatisticByProjectId(@Param("projectId") Integer projectId);

    @Query("select ticket.type as name, count(ticket.type) as count "
            + "from Ticket ticket "
            + "where ticket.projectId = :projectId "
            + "and ticket.status <> 'CLOSED'"
            + "group by ticket.type")
    List<TicketTypeStatisticEntry> getTicketTypeStatisticByProjectId(@Param("projectId") Integer projectId);

    @Query(value = "select to_char(created_when, 'YYYY-MM') as name, count(*) as count "
            + "from pr_ticket "
            + "where project_id = :projectId "
            + "and \"type\" = 'DEFECT' "
            + "group by to_char(created_when, 'YYYY-MM') "
            + "order by 1", nativeQuery = true)
    List<StatisticEntry> getOpenedDefectsStatisticByProjectId(@Param("projectId") Integer projectId);

    @Query(value = "select to_char(modified_when, 'YYYY-MM') as name, count(*) as count "
            + "from pr_ticket "
            + "where project_id = :projectId "
            + "and \"type\" = 'DEFECT' "
            + "and status = 'CLOSED' "
            + "group by to_char(modified_when, 'YYYY-MM') "
            + "order by 1", nativeQuery = true)
    List<StatisticEntry> getClosedDefectsStatisticByProjectId(@Param("projectId") Integer projectId);

}
