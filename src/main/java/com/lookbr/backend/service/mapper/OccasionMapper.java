package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.OccasionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Occasion and its DTO OccasionDTO.
 */
@Mapper(componentModel = "spring", uses = {InspirationMapper.class, LookMapper.class})
public interface OccasionMapper extends EntityMapper<OccasionDTO, Occasion> {

    @Mapping(source = "inspiration.id", target = "inspirationId")
    @Mapping(source = "look.id", target = "lookId")
    OccasionDTO toDto(Occasion occasion); 

    @Mapping(source = "inspirationId", target = "inspiration")
    @Mapping(source = "lookId", target = "look")
    Occasion toEntity(OccasionDTO occasionDTO);

    default Occasion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Occasion occasion = new Occasion();
        occasion.setId(id);
        return occasion;
    }
}
