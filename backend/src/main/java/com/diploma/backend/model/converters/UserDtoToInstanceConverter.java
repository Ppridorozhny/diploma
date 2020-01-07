package com.diploma.backend.model.converters;

import com.diploma.backend.AppConstants;
import com.diploma.backend.error.exceptions.ResourceNotFoundException;
import com.diploma.backend.model.dto.RoleDTO;
import com.diploma.backend.model.dto.UserDTO;
import com.diploma.backend.model.entities.Role;
import com.diploma.backend.model.entities.User;
import com.diploma.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDtoToInstanceConverter implements Converter<UserDTO, User> {

    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    @Override
    public User convert(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        final Set<Role> roles = userDTO.getRoles().stream()
                .filter(Objects::nonNull)
                .map(RoleDTO::getName)
                .filter(Objects::nonNull)
                .map(this::getRoleByName)
                .collect(Collectors.toSet());

        user.setRoles(CollectionUtils.isEmpty(roles) ? Collections.singleton(getRoleByName(AppConstants.ROLE_USER)) : roles);

        return user;
    }

    private Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));
    }

}
