package com.diploma.backend.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RunDTO {

    private Integer id;

    private TicketShortDTO ticket;

    private String seriesId;

    private Date startedWhen;

    private Date completedWhen;

}
