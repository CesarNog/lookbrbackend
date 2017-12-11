package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Inspiration;

import com.lookbr.backend.domain.Intention;

import com.lookbr.backend.domain.Look;

import com.lookbr.backend.service.dto.IntentionDTO;

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

public class IntentionMapperImpl implements IntentionMapper {

    @Autowired

    private InspirationMapper inspirationMapper;

    @Autowired

    private LookMapper lookMapper;

    @Override

    public List<Intention> toEntity(List<IntentionDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Intention> list = new ArrayList<Intention>();

        for ( IntentionDTO intentionDTO : dtoList ) {

            list.add( toEntity( intentionDTO ) );
        }

        return list;
    }

    @Override

    public List<IntentionDTO> toDto(List<Intention> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<IntentionDTO> list = new ArrayList<IntentionDTO>();

        for ( Intention intention : entityList ) {

            list.add( toDto( intention ) );
        }

        return list;
    }

    @Override

    public IntentionDTO toDto(Intention intention) {

        if ( intention == null ) {

            return null;
        }

        IntentionDTO intentionDTO_ = new IntentionDTO();

        intentionDTO_.setLookId( intentionLookId( intention ) );

        intentionDTO_.setInspirationId( intentionInspirationId( intention ) );

        intentionDTO_.setId( intention.getId() );

        intentionDTO_.setPage( intention.getPage() );

        return intentionDTO_;
    }

    @Override

    public Intention toEntity(IntentionDTO intentionDTO) {

        if ( intentionDTO == null ) {

            return null;
        }

        Intention intention_ = new Intention();

        intention_.setLook( lookMapper.fromId( intentionDTO.getLookId() ) );

        intention_.setInspiration( inspirationMapper.fromId( intentionDTO.getInspirationId() ) );

        intention_.setId( intentionDTO.getId() );

        intention_.setPage( intentionDTO.getPage() );

        return intention_;
    }

    private Long intentionLookId(Intention intention) {

        if ( intention == null ) {

            return null;
        }

        Look look = intention.getLook();

        if ( look == null ) {

            return null;
        }

        Long id = look.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long intentionInspirationId(Intention intention) {

        if ( intention == null ) {

            return null;
        }

        Inspiration inspiration = intention.getInspiration();

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

