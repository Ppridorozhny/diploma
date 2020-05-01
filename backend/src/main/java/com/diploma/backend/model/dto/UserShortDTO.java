package com.diploma.backend.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.diploma.backend.validation.Groups;
import com.diploma.backend.validation.UniqueEmailConstraint;
import com.diploma.backend.validation.UniqueUsernameConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDTO {

    @NotNull(groups = {Groups.UPDATE.class})
    private Integer id;

    @NotNull
    @NotBlank
    @Length(max = 100)
    private String firstName;

    @NotNull
    @NotBlank
    @Length(max = 100)
    private String lastName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[_.@A-Za-z0-9-]*$")
    @Length(max = 100)
    @UniqueUsernameConstraint
    private String username;

    @Email
    @NotBlank
    @Length(max = 100)
    @UniqueEmailConstraint(groups = {Groups.CREATE.class})
    private String email;

}
