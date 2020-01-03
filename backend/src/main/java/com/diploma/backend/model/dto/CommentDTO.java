package com.diploma.backend.model.dto;

import com.diploma.backend.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @NotNull(groups = {Groups.UPDATE.class})
    private Long id;

    @NotNull
    @Length(max = 100)
    private String text;

    @EqualsAndHashCode.Exclude
    private TicketDTO ticketDTO;
}
