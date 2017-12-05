package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.ConsultantsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Consultants and its DTO ConsultantsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConsultantsMapper extends EntityMapper<ConsultantsDTO, Consultants> {

    

    

    default Consultants fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consultants consultants = new Consultants();
        consultants.setId(id);
        return consultants;
    }
}
