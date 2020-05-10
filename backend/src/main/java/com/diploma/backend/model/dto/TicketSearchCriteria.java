package com.diploma.backend.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.diploma.backend.model.enums.Priority;
import com.diploma.backend.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class TicketSearchCriteria {

    @NotBlank
    @NotNull
    @Length(max = 100)
    private String name;
    @Length(max = 255)
    private String description;
    private Priority priority;
    private Status status;

}
