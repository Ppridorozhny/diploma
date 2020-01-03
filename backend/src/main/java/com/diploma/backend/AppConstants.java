package com.diploma.backend;

import com.diploma.backend.model.dto.TicketDTO;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AppConstants {

    public static final Type TICKET_LIST_TYPE = (new TypeToken<List<TicketDTO>>() {
    }).getType();

    private AppConstants() {
    }
}
