package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.LoginDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Login and its DTO LoginDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface LoginMapper extends EntityMapper<LoginDTO, Login> {

    @Mapping(source = "user.id", target = "userId")
    LoginDTO toDto(Login login); 

    @Mapping(source = "userId", target = "user")
    Login toEntity(LoginDTO loginDTO);

    default Login fromId(Long id) {
        if (id == null) {
            return null;
        }
        Login login = new Login();
        login.setId(id);
        return login;
    }
}
