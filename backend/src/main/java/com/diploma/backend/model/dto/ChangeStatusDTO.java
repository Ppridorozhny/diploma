package com.diploma.backend.model.dto;

import javax.validation.constraints.NotNull;

import com.diploma.backend.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusDTO {

    @NotNull
    private Integer ticketId;

    @NotNull
    private Status newStatus;

    private String comment;

}
