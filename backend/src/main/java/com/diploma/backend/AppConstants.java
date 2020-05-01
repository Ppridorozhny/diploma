package com.diploma.backend;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import com.diploma.backend.model.dto.ProjectDTO;
import com.diploma.backend.model.dto.RunDTO;
import com.diploma.backend.model.dto.TicketDTO;
import com.diploma.backend.model.dto.UserShortDTO;

public class AppConstants {

    public static final Type TICKET_LIST_TYPE = (new TypeToken<List<TicketDTO>>() {
    }).getType();

    public static final Type USER_SHORT_LIST_TYPE = (new TypeToken<List<UserShortDTO>>() {
    }).getType();

    public static final Type RUN_LIST_TYPE = (new TypeToken<List<RunDTO>>() {
    }).getType();

    public static final Type PROJECT_LIST_TYPE = (new TypeToken<List<ProjectDTO>>() {
    }).getType();

    public static final String ROLE_USER = "USER";

    public static final String ROLE_ADMIN = "ADMIN";

    public static final String TOKEN_TYPE = "Bearer ";

    private AppConstants() {
    }

}
