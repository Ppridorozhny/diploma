package com.diploma.backend.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@Entity
@Table(name = "pr_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Length(max = 100)
    private String firstName;

    @NotNull
    @Length(max = 100)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^[_.@A-Za-z0-9-]*$")
    @Length(max = 100)
    private String username;

    @NotNull
    @Length(max = 100)
    private String password;

    @OneToMany
    @JoinTable(name = "pr_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Role> roles;
}
