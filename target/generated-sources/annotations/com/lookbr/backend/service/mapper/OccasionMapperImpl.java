package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Inspiration;

import com.lookbr.backend.domain.Look;

import com.lookbr.backend.domain.Occasion;

import com.lookbr.backend.service.dto.OccasionDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-12-06T15:37:21-0200",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"

)

@Component

public class OccasionMapperImpl implements OccasionMapper {

    @Autowired

    private InspirationMapper inspirationMapper;

    @Autowired

    private LookMapper lookMapper;

    @Override

    public List<Occasion> toEntity(List<OccasionDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Occasion> list = new ArrayList<Occasion>();

        for ( OccasionDTO occasionDTO : dtoList ) {

            list.add( toEntity( occasionDTO ) );
        }

        return list;
    }

    @Override

    public List<OccasionDTO> toDto(List<Occasion> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<OccasionDTO> list = new ArrayList<OccasionDTO>();

        for ( Occasion occasion : entityList ) {

            list.add( toDto( occasion ) );
        }

        return list;
    }

    @Override

    public OccasionDTO toDto(Occasion occasion) {

        if ( occasion == null ) {

            return null;
        }

        OccasionDTO occasionDTO_ = new OccasionDTO();

        occasionDTO_.setLookId( occasionLookId( occasion ) );

        occasionDTO_.setInspirationId( occasionInspirationId( occasion ) );

        occasionDTO_.setId( occasion.getId() );

        occasionDTO_.setPage( occasion.getPage() );

        return occasionDTO_;
    }

    @Override

    public Occasion toEntity(OccasionDTO occasionDTO) {

        if ( occasionDTO == null ) {

            return null;
        }

        Occasion occasion_ = new Occasion();

        occasion_.setLook( lookMapper.fromId( occasionDTO.getLookId() ) );

        occasion_.setInspiration( inspirationMapper.fromId( occasionDTO.getInspirationId() ) );

        occasion_.setId( occasionDTO.getId() );

        occasion_.setPage( occasionDTO.getPage() );

        return occasion_;
    }

    private Long occasionLookId(Occasion occasion) {

        if ( occasion == null ) {

            return null;
        }

        Look look = occasion.getLook();

        if ( look == null ) {

            return null;
        }

        Long id = look.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long occasionInspirationId(Occasion occasion) {

        if ( occasion == null ) {

            return null;
        }

        Inspiration inspiration = occasion.getInspiration();

        if ( inspiration == null ) {

            return null;
        }

        Long id = inspiration.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

