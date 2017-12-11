package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.DayTimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DayTime and its DTO DayTimeDTO.
 */
@Mapper(componentModel = "spring", uses = {InspirationMapper.class})
public interface DayTimeMapper extends EntityMapper<DayTimeDTO, DayTime> {

    @Mapping(source = "inspiration.id", target = "inspirationId")
    DayTimeDTO toDto(DayTime dayTime); 

    @Mapping(source = "inspirationId", target = "inspiration")
    DayTime toEntity(DayTimeDTO dayTimeDTO);

    default DayTime fromId(Long id) {
        if (id == null) {
            return null;
        }
        DayTime dayTime = new DayTime();
        dayTime.setId(id);
        return dayTime;
    }
}
