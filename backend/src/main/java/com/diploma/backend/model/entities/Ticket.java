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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Length;

import com.diploma.backend.model.enums.Priority;
import com.diploma.backend.model.enums.Resolution;
import com.diploma.backend.model.enums.Status;
import com.diploma.backend.model.enums.TicketType;
import com.diploma.backend.model.type.PostgresEnumType;
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
@TypeDef(name = "pg_enum", typeClass = PostgresEnumType.class)
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
    @Type(type = "pg_enum")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @NotNull
    @Type(type = "pg_enum")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Type(type = "pg_enum")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TicketType type;

    @NotNull
    @Type(type = "pg_enum")
    @Enumerated(EnumType.STRING)
    private Resolution resolution;

    @NotNull
    private Date dueDate;

    private String seriesId;

    private Integer assigneeId;

    private Integer reporterId;

    private Integer epicId;

    @ElementCollection
    @CollectionTable(name = "pr_label", joinColumns = @JoinColumn(name = "ticket_id"))
    @Column(name = "name")
    @EqualsAndHashCode.Exclude
    private List<String> labels;

    @OneToMany(mappedBy = "source")
    @EqualsAndHashCode.Exclude
    private Set<TicketRelation> relations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Project project;

}
