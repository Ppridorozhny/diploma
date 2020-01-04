package com.diploma.backend.model.dto;

import com.diploma.backend.validation.Groups;
import com.diploma.backend.validation.UniqueEmailConstraint;
import com.diploma.backend.validation.UniqueUsernameConstraint;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
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
    @UniqueUsernameConstraint
    private String username;

    @Email
    @NotBlank
    @Length(max = 100)
    @UniqueEmailConstraint(groups = {Groups.CREATE.class})
    private String email;

    @NotNull
    @NotBlank
    @Length(max = 100)
    @ToString.Exclude
    private String password;

    @NotEmpty
    @NotNull
    @EqualsAndHashCode.Exclude
    private Set<RoleDTO> roleDTOS;
}
