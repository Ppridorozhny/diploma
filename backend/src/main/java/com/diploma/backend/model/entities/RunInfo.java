package com.diploma.backend.model.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RunInfo {

    private Integer ticketId;

    private String ticketName;

    private Integer parentId;

    private Date startedWhen;

    private Date completedWhen;

}
