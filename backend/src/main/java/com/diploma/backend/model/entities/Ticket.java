package com.diploma.backend.model.entities;

import com.diploma.backend.model.enums.Priority;
import com.diploma.backend.model.enums.Resolution;
import com.diploma.backend.model.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false, updatable = false)
    @NotNull
    @EqualsAndHashCode.Exclude
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epic_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Ticket epic;

    @ElementCollection
    @CollectionTable(name = "pr_label", joinColumns = @JoinColumn(name = "label_id"))
    @Column(name = "name")
    @EqualsAndHashCode.Exclude
    private List<String> label;

    @OneToMany(mappedBy = "source")
    @EqualsAndHashCode.Exclude
    private Set<TicketRelation> relations;

    //todo add component
}
