package com.diploma.backend.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

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

    @OneToMany
    @JoinTable(name = "pr_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Role> roles;

}
