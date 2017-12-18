package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.TemperatureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Temperature and its DTO TemperatureDTO.
 */
@Mapper(componentModel = "spring", uses = {InspirationMapper.class, LookMapper.class})
public interface TemperatureMapper extends EntityMapper<TemperatureDTO, Temperature> {

    @Mapping(source = "inspiration.id", target = "inspirationId")
    @Mapping(source = "look.id", target = "lookId")
    TemperatureDTO toDto(Temperature temperature); 

    @Mapping(source = "inspirationId", target = "inspiration")
    @Mapping(source = "lookId", target = "look")
    Temperature toEntity(TemperatureDTO temperatureDTO);

    default Temperature fromId(Long id) {
        if (id == null) {
            return null;
        }
        Temperature temperature = new Temperature();
        temperature.setId(id);
        return temperature;
    }
}
