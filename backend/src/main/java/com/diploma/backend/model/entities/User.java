package com.diploma.backend.model.entities;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Data
@Entity
@Table(name = "pr_user")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotNull
    @Length(max = 100)
    private String firstName;

    @NotBlank
    @NotNull
    @Length(max = 100)
    private String lastName;

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[_.@A-Za-z0-9-]*$")
    @Length(max = 100)
    @Column(unique = true, nullable = false)
    private String username;

    @Email
    @NotBlank
    @NotNull
    @Length(max = 100)
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Length(max = 100)
    @ToString.Exclude
    private String password;

    @NotEmpty
    @NotNull
    @OneToMany
    @JoinTable(name = "pr_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Role> roles;
}
