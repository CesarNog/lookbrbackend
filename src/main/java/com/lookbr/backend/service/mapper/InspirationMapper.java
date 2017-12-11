package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.InspirationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Inspiration and its DTO InspirationDTO.
 */
@Mapper(componentModel = "spring", uses = {TimelineMapper.class})
public interface InspirationMapper extends EntityMapper<InspirationDTO, Inspiration> {

    @Mapping(source = "timeline.id", target = "timelineId")
    InspirationDTO toDto(Inspiration inspiration); 

    @Mapping(source = "timelineId", target = "timeline")
    @Mapping(target = "intentions", ignore = true)
    @Mapping(target = "occasions", ignore = true)
    @Mapping(target = "temperatures", ignore = true)
    @Mapping(target = "dayTimes", ignore = true)
    Inspiration toEntity(InspirationDTO inspirationDTO);

    default Inspiration fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inspiration inspiration = new Inspiration();
        inspiration.setId(id);
        return inspiration;
    }
}
