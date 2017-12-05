package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.LoginDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Login and its DTO LoginDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LoginMapper extends EntityMapper<LoginDTO, Login> {

    

    

    default Login fromId(Long id) {
        if (id == null) {
            return null;
        }
        Login login = new Login();
        login.setId(id);
        return login;
    }
}
