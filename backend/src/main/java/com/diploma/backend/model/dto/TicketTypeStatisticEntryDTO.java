package com.diploma.backend.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.diploma.backend.model.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketTypeStatisticEntryDTO {

    @NotBlank
    @NotNull
    private TicketType name;

    @NotNull
    private Integer count;

}
