package com.diploma.backend;

import com.diploma.backend.model.dto.TicketDTO;
import com.diploma.backend.model.dto.UserDTO;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AppConstants {

    public static final Type TICKET_LIST_TYPE = (new TypeToken<List<TicketDTO>>() {
    }).getType();

    public static final Type USER_LIST_TYPE = (new TypeToken<List<UserDTO>>() {
    }).getType();

    public static final String TOKEN_TYPE = "Bearer ";

    private AppConstants() {
    }
}
