package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.SocialMediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SocialMedia and its DTO SocialMediaDTO.
 */
@Mapper(componentModel = "spring", uses = {ConsultantMapper.class})
public interface SocialMediaMapper extends EntityMapper<SocialMediaDTO, SocialMedia> {

    @Mapping(source = "consultant.id", target = "consultantId")
    SocialMediaDTO toDto(SocialMedia socialMedia); 

    @Mapping(source = "consultantId", target = "consultant")
    SocialMedia toEntity(SocialMediaDTO socialMediaDTO);

    default SocialMedia fromId(Long id) {
        if (id == null) {
            return null;
        }
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setId(id);
        return socialMedia;
    }
}
