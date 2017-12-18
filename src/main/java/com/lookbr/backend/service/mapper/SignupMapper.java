package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.SignupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Signup and its DTO SignupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SignupMapper extends EntityMapper<SignupDTO, Signup> {

    

    

    default Signup fromId(Long id) {
        if (id == null) {
            return null;
        }
        Signup signup = new Signup();
        signup.setId(id);
        return signup;
    }
}
