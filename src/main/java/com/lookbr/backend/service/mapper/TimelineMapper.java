package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.TimelineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Timeline and its DTO TimelineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TimelineMapper extends EntityMapper<TimelineDTO, Timeline> {

    

    

    default Timeline fromId(Long id) {
        if (id == null) {
            return null;
        }
        Timeline timeline = new Timeline();
        timeline.setId(id);
        return timeline;
    }
}
