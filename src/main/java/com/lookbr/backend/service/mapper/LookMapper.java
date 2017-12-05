package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.LookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Look and its DTO LookDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LookMapper extends EntityMapper<LookDTO, Look> {

    

    

    default Look fromId(Long id) {
        if (id == null) {
            return null;
        }
        Look look = new Look();
        look.setId(id);
        return look;
    }
}
