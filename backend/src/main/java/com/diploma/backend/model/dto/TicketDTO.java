package com.diploma.backend.model.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.diploma.backend.model.enums.Priority;
import com.diploma.backend.model.enums.Resolution;
import com.diploma.backend.model.enums.Status;
import com.diploma.backend.model.enums.TicketType;
import com.diploma.backend.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private Status status;

    @NotNull
    private Resolution resolution;

    @NotNull
    private Date dueDate;

    private Integer assigneeId;

    @NotNull
    private Integer reporterId;

    private Integer epicId;

    @EqualsAndHashCode.Exclude
    private List<String> labels;

    @NotNull
    private Integer projectId;

}
