package com.diploma.backend.model.dto;

import com.diploma.backend.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    @NotNull(groups = {Groups.UPDATE.class})
    private Integer id;

    @NotBlank
    @NotNull
    @Length(max = 100)
    private String name;

    @EqualsAndHashCode.Exclude
    private Set<UserDTO> roles;
}
