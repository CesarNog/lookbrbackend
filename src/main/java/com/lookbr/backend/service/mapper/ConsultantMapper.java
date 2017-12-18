package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.ConsultantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Consultant and its DTO ConsultantDTO.
 */
@Mapper(componentModel = "spring", uses = {LookMapper.class})
public interface ConsultantMapper extends EntityMapper<ConsultantDTO, Consultant> {

    @Mapping(source = "look.id", target = "lookId")
    ConsultantDTO toDto(Consultant consultant); 

    @Mapping(target = "socialMedias", ignore = true)
    @Mapping(source = "lookId", target = "look")
    Consultant toEntity(ConsultantDTO consultantDTO);

    default Consultant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consultant consultant = new Consultant();
        consultant.setId(id);
        return consultant;
    }
}
