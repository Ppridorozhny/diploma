package com.diploma.backend.model.entities;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.diploma.backend.model.enums.Priority;
import com.diploma.backend.model.enums.Resolution;
import com.diploma.backend.model.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "pr_ticket")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Ticket extends BaseAuditEntity {

    @Id
    @Column(name = "ticket_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotNull
    @Length(max = 100)
    private String name;

    @Length(max = 255)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", updatable = false, nullable = false)
    private TicketType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Resolution resolution;

    @NotNull
    private Date dueDate;

    private String seriesId;

    private Integer assigneeId;

    private Integer reporterId;

    private Integer epicId;

    @ElementCollection
    @CollectionTable(name = "pr_label", joinColumns = @JoinColumn(name = "label_id"))
    @Column(name = "name")
    @EqualsAndHashCode.Exclude
    private List<String> label;

    @OneToMany(mappedBy = "source")
    @EqualsAndHashCode.Exclude
    private Set<TicketRelation> relations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Project project;

}
