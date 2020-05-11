package com.diploma.backend.model.dto;

import javax.validation.constraints.NotNull;

import com.diploma.backend.model.enums.RelationType;
import com.diploma.backend.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRelationDTO {

    @NotNull(groups = {Groups.UPDATE.class})
    private Integer id;

    @NotNull
    private RelationType relationType;

    @NotNull
    @EqualsAndHashCode.Exclude
    private TicketShortDTO source;

    @NotNull
    @EqualsAndHashCode.Exclude
    private TicketShortDTO target;

}
