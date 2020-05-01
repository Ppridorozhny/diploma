package com.diploma.backend.model.entities;

import java.time.Instant;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "pr_run")
@NoArgsConstructor
@AllArgsConstructor
public class Run {

    @Id
    @Column(name = "run_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ticket_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Ticket ticket;

    private String seriesId;

    private Date startedWhen = Date.from(Instant.now());

    private Date completedWhen;

}
