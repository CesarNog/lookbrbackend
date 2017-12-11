package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.ConsultantVoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConsultantVote and its DTO ConsultantVoteDTO.
 */
@Mapper(componentModel = "spring", uses = {LookMapper.class})
public interface ConsultantVoteMapper extends EntityMapper<ConsultantVoteDTO, ConsultantVote> {

    @Mapping(source = "look.id", target = "lookId")
    ConsultantVoteDTO toDto(ConsultantVote consultantVote); 

    @Mapping(source = "lookId", target = "look")
    ConsultantVote toEntity(ConsultantVoteDTO consultantVoteDTO);

    default ConsultantVote fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsultantVote consultantVote = new ConsultantVote();
        consultantVote.setId(id);
        return consultantVote;
    }
}
