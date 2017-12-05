package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.IntentionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Intention and its DTO IntentionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IntentionMapper extends EntityMapper<IntentionDTO, Intention> {

    

    

    default Intention fromId(Long id) {
        if (id == null) {
            return null;
        }
        Intention intention = new Intention();
        intention.setId(id);
        return intention;
    }
}
