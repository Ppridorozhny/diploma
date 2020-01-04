package com.diploma.backend.model.converters;

import com.diploma.backend.error.exceptions.ResourceNotFoundException;
import com.diploma.backend.model.dto.UserDTO;
import com.diploma.backend.model.entities.Role;
import com.diploma.backend.model.entities.User;
import com.diploma.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
        final Set<Role> roles = userDTO.getRoleDTOS().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", role.getName())))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        return user;
    }
}
