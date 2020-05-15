package com.diploma.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diploma.backend.model.enums.Status;
import com.diploma.backend.model.enums.TicketType;
import com.diploma.backend.service.impl.DictionaryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"DictionaryController"})
@Validated
@RestController
@RequestMapping("/api/dictionary")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/available-statuses")
    public Status[] getAvailableStatuses(@RequestParam Status currentStatus) {
        log.debug("Get available statuses");
        return dictionaryService.getAvailableStatuses(currentStatus);
    }

    @GetMapping("/available-types")
    public TicketType[] getAvailableTypes(@RequestParam TicketType currentType) {
        return  dictionaryService.getAvailableTypes(currentType);
    }

}
