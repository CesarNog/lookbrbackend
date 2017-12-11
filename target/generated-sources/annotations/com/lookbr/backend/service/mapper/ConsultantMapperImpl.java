package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Consultant;

import com.lookbr.backend.domain.Look;

import com.lookbr.backend.service.dto.ConsultantDTO;

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

public class ConsultantMapperImpl implements ConsultantMapper {

    @Autowired

    private LookMapper lookMapper;

    @Override

    public List<Consultant> toEntity(List<ConsultantDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Consultant> list = new ArrayList<Consultant>();

        for ( ConsultantDTO consultantDTO : dtoList ) {

            list.add( toEntity( consultantDTO ) );
        }

        return list;
    }

    @Override

    public List<ConsultantDTO> toDto(List<Consultant> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<ConsultantDTO> list = new ArrayList<ConsultantDTO>();

        for ( Consultant consultant : entityList ) {

            list.add( toDto( consultant ) );
        }

        return list;
    }

    @Override

    public ConsultantDTO toDto(Consultant consultant) {

        if ( consultant == null ) {

            return null;
        }

        ConsultantDTO consultantDTO_ = new ConsultantDTO();

        consultantDTO_.setLookId( consultantLookId( consultant ) );

        consultantDTO_.setId( consultant.getId() );

        consultantDTO_.setConsultantId( consultant.getConsultantId() );

        consultantDTO_.setConsultantName( consultant.getConsultantName() );

        consultantDTO_.setConsultantDescription( consultant.getConsultantDescription() );

        consultantDTO_.setConsultantCoverPhotoURL( consultant.getConsultantCoverPhotoURL() );

        consultantDTO_.setConsultantProfilePhotoURL( consultant.getConsultantProfilePhotoURL() );

        consultantDTO_.setCharge( consultant.getCharge() );

        consultantDTO_.setInspirationURL( consultant.getInspirationURL() );

        consultantDTO_.setProfilePhoto( consultant.getProfilePhoto() );

        consultantDTO_.setStatus( consultant.getStatus() );

        consultantDTO_.setPage( consultant.getPage() );

        return consultantDTO_;
    }

    @Override

    public Consultant toEntity(ConsultantDTO consultantDTO) {

        if ( consultantDTO == null ) {

            return null;
        }

        Consultant consultant_ = new Consultant();

        consultant_.setLook( lookMapper.fromId( consultantDTO.getLookId() ) );

        consultant_.setId( consultantDTO.getId() );

        consultant_.setConsultantId( consultantDTO.getConsultantId() );

        consultant_.setConsultantName( consultantDTO.getConsultantName() );

        consultant_.setConsultantDescription( consultantDTO.getConsultantDescription() );

        consultant_.setConsultantCoverPhotoURL( consultantDTO.getConsultantCoverPhotoURL() );

        consultant_.setConsultantProfilePhotoURL( consultantDTO.getConsultantProfilePhotoURL() );

        consultant_.setCharge( consultantDTO.getCharge() );

        consultant_.setInspirationURL( consultantDTO.getInspirationURL() );

        consultant_.setProfilePhoto( consultantDTO.getProfilePhoto() );

        consultant_.setStatus( consultantDTO.getStatus() );

        consultant_.setPage( consultantDTO.getPage() );

        return consultant_;
    }

    private Long consultantLookId(Consultant consultant) {

        if ( consultant == null ) {

            return null;
        }

        Look look = consultant.getLook();

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

