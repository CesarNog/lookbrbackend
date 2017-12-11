package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.LookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Look and its DTO LookDTO.
 */
@Mapper(componentModel = "spring", uses = {ClosetMapper.class})
public interface LookMapper extends EntityMapper<LookDTO, Look> {

    @Mapping(source = "closet.id", target = "closetId")
    LookDTO toDto(Look look); 

    @Mapping(target = "consultantsVotes", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "intentions", ignore = true)
    @Mapping(target = "occasions", ignore = true)
    @Mapping(target = "temperatures", ignore = true)
    @Mapping(target = "consultants", ignore = true)
    @Mapping(source = "closetId", target = "closet")
    Look toEntity(LookDTO lookDTO);

    default Look fromId(Long id) {
        if (id == null) {
            return null;
        }
        Look look = new Look();
        look.setId(id);
        return look;
    }
}
