package com.diploma.backend.model.dto;

import com.diploma.backend.model.enums.Priority;
import com.diploma.backend.model.enums.Resolution;
import com.diploma.backend.model.enums.TicketType;
import com.diploma.backend.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    @NotNull(groups = {Groups.UPDATE.class})
    private Integer id;

    @NotBlank
    @NotNull
    @Length(max = 100)
    private String name;

    @Length(max = 255)
    private String description;

    @NotNull
    private Priority priority;

    @NotNull
    private TicketType type;

    @NotNull
    private Resolution resolution;

    @NotNull
    private Date dueDate;

    @EqualsAndHashCode.Exclude
    private UserDTO assignee;

    @NotNull
    @EqualsAndHashCode.Exclude
    private UserDTO reporter;

    @EqualsAndHashCode.Exclude
    private TicketDTO epic;

    @EqualsAndHashCode.Exclude
    private List<String> label;

    @EqualsAndHashCode.Exclude
    private Set<TicketRelationDTO> relations;
}
