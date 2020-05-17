package com.diploma.backend.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunInfoDTO {

    private Integer ticketId;

    private String ticketName;

    private Integer parentId;

    private Date startedWhen;

    private Date completedWhen;

}
