package com.diploma.backend.service.impl;

import static com.diploma.backend.model.enums.Status.AFTER_ASSESSED;
import static com.diploma.backend.model.enums.Status.AFTER_CLOSED;
import static com.diploma.backend.model.enums.Status.AFTER_IMPLEMENTED;
import static com.diploma.backend.model.enums.Status.AFTER_IN_ASSESSMENT;
import static com.diploma.backend.model.enums.Status.AFTER_IN_PROGRESS;
import static com.diploma.backend.model.enums.Status.AFTER_OPEN;
import static com.diploma.backend.model.enums.Status.AFTER_READY_FOR_TESTING;

import org.springframework.stereotype.Service;

import com.diploma.backend.model.enums.Status;
import com.diploma.backend.model.enums.TicketType;

@Service
public class DictionaryService {

    public Status[] getAvailableStatuses(Status currentStatus) {
        Status[] availableStatuses;
        switch (currentStatus) {
            case OPEN:
                availableStatuses = AFTER_OPEN;
                break;
            case IN_ASSESSMENT:
                availableStatuses = AFTER_IN_ASSESSMENT;
                break;
            case ASSESSED:
                availableStatuses = AFTER_ASSESSED;
                break;
            case IN_PROGRESS:
                availableStatuses = AFTER_IN_PROGRESS;
                break;
            case IMPLEMENTED:
                availableStatuses = AFTER_IMPLEMENTED;
                break;
            case READY_FOR_TESTING:
                availableStatuses = AFTER_READY_FOR_TESTING;
                break;
            case CLOSED:
                availableStatuses = AFTER_CLOSED;
                break;
            default:
                availableStatuses = new Status[0];
        }
        return availableStatuses;
    }

    public TicketType[] getAvailableTypes(TicketType currentType) {
        TicketType[] availableTypes;
        switch (currentType) {
            case DEFAULT:
                availableTypes = TicketType.FOR_DEFAULT;
                break;
            case EPIC:
                availableTypes = TicketType.FOR_EPIC;
                break;
            case TASK:
                availableTypes = TicketType.FOR_TASK;
                break;
            case STORY:
                availableTypes = TicketType.FOR_STORY;
                break;
            case DEFECT:
            case SUBTASK:
            default:
                availableTypes = new TicketType[0];
        }
        return availableTypes;
    }

}
