package com.diploma.backend.model.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends UserShortDTO {

    @NotNull
    @NotBlank
    @Length(max = 100)
    @ToString.Exclude
    private String password;

    @NotEmpty
    @NotNull
    @EqualsAndHashCode.Exclude
    private Set<RoleDTO> roles;

}
