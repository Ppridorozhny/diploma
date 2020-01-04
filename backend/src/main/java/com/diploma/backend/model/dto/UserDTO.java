package com.diploma.backend.model.dto;

import com.diploma.backend.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

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
    private String username;

    @Email
    @NotBlank
    @Length(max = 100)
    private String email;

    @NotNull
    @NotBlank
    @Length(max = 100)
    private String password;

    @EqualsAndHashCode.Exclude
    private Set<RoleDTO> roleDTOS;
}
