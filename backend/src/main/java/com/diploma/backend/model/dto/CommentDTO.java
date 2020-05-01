package com.diploma.backend.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.diploma.backend.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @NotNull(groups = {Groups.UPDATE.class})
    private Long id;

    @NotBlank
    @NotNull
    @Length(max = 100)
    private String text;

    @EqualsAndHashCode.Exclude
    private TicketDTO ticketDTO;

}
