package com.diploma.backend.model.pojo;

import javax.validation.constraints.NotNull;

import com.diploma.backend.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatus {

    @NotNull
    private Integer ticketId;

    @NotNull
    private Status newStatus;

    private String comment;

}
