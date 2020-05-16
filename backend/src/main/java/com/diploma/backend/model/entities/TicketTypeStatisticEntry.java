package com.diploma.backend.model.entities;

import com.diploma.backend.model.enums.TicketType;

public interface TicketTypeStatisticEntry {

    TicketType getName();

    Integer getCount();

}
