package com.diploma.backend.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticDTO {

    @NotBlank
    @NotNull
    private String username;

    @NotNull
    private Integer count;

}
