package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.ConsultantVote;

import com.lookbr.backend.domain.Look;

import com.lookbr.backend.service.dto.ConsultantVoteDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-12-06T15:37:22-0200",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"

)

@Component

public class ConsultantVoteMapperImpl implements ConsultantVoteMapper {

    @Autowired

    private LookMapper lookMapper;

    @Override

    public List<ConsultantVote> toEntity(List<ConsultantVoteDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<ConsultantVote> list = new ArrayList<ConsultantVote>();

        for ( ConsultantVoteDTO consultantVoteDTO : dtoList ) {

            list.add( toEntity( consultantVoteDTO ) );
        }

        return list;
    }

    @Override

    public List<ConsultantVoteDTO> toDto(List<ConsultantVote> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<ConsultantVoteDTO> list = new ArrayList<ConsultantVoteDTO>();

        for ( ConsultantVote consultantVote : entityList ) {

            list.add( toDto( consultantVote ) );
        }

        return list;
    }

    @Override

    public ConsultantVoteDTO toDto(ConsultantVote consultantVote) {

        if ( consultantVote == null ) {

            return null;
        }

        ConsultantVoteDTO consultantVoteDTO_ = new ConsultantVoteDTO();

        consultantVoteDTO_.setLookId( consultantVoteLookId( consultantVote ) );

        consultantVoteDTO_.setId( consultantVote.getId() );

        consultantVoteDTO_.setConsultantProfilePhotoUrl( consultantVote.getConsultantProfilePhotoUrl() );

        return consultantVoteDTO_;
    }

    @Override

    public ConsultantVote toEntity(ConsultantVoteDTO consultantVoteDTO) {

        if ( consultantVoteDTO == null ) {

            return null;
        }

        ConsultantVote consultantVote_ = new ConsultantVote();

        consultantVote_.setLook( lookMapper.fromId( consultantVoteDTO.getLookId() ) );

        consultantVote_.setId( consultantVoteDTO.getId() );

        consultantVote_.setConsultantProfilePhotoUrl( consultantVoteDTO.getConsultantProfilePhotoUrl() );

        return consultantVote_;
    }

    private Long consultantVoteLookId(ConsultantVote consultantVote) {

        if ( consultantVote == null ) {

            return null;
        }

        Look look = consultantVote.getLook();

        if ( look == null ) {

            return null;
        }

        Long id = look.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

