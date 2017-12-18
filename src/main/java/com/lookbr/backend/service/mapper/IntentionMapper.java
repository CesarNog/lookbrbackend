package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.IntentionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Intention and its DTO IntentionDTO.
 */
@Mapper(componentModel = "spring", uses = {InspirationMapper.class, LookMapper.class})
public interface IntentionMapper extends EntityMapper<IntentionDTO, Intention> {

    @Mapping(source = "inspiration.id", target = "inspirationId")
    @Mapping(source = "look.id", target = "lookId")
    IntentionDTO toDto(Intention intention); 

    @Mapping(source = "inspirationId", target = "inspiration")
    @Mapping(source = "lookId", target = "look")
    Intention toEntity(IntentionDTO intentionDTO);

    default Intention fromId(Long id) {
        if (id == null) {
            return null;
        }
        Intention intention = new Intention();
        intention.setId(id);
        return intention;
    }
}
