package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.ClosetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Closet and its DTO ClosetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClosetMapper extends EntityMapper<ClosetDTO, Closet> {

    

    

    default Closet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Closet closet = new Closet();
        closet.setId(id);
        return closet;
    }
}
