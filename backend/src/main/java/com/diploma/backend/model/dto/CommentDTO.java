package com.diploma.backend.model.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.diploma.backend.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    private Integer ticketId;

    private String createdBy;

    private Date createdWhen;

}
