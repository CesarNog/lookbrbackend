package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Closet;

import com.lookbr.backend.domain.Look;

import com.lookbr.backend.service.dto.LookDTO;

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

public class LookMapperImpl implements LookMapper {

    @Autowired

    private ClosetMapper closetMapper;

    @Override

    public List<Look> toEntity(List<LookDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Look> list = new ArrayList<Look>();

        for ( LookDTO lookDTO : dtoList ) {

            list.add( toEntity( lookDTO ) );
        }

        return list;
    }

    @Override

    public List<LookDTO> toDto(List<Look> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<LookDTO> list = new ArrayList<LookDTO>();

        for ( Look look : entityList ) {

            list.add( toDto( look ) );
        }

        return list;
    }

    @Override

    public LookDTO toDto(Look look) {

        if ( look == null ) {

            return null;
        }

        LookDTO lookDTO_ = new LookDTO();

        lookDTO_.setClosetId( lookClosetId( look ) );

        lookDTO_.setId( look.getId() );

        lookDTO_.setUserId( look.getUserId() );

        lookDTO_.setTemperature( look.getTemperature() );

        lookDTO_.setDayTime( look.getDayTime() );

        lookDTO_.setPictureIndex( look.getPictureIndex() );

        lookDTO_.setUrl( look.getUrl() );

        return lookDTO_;
    }

    @Override

    public Look toEntity(LookDTO lookDTO) {

        if ( lookDTO == null ) {

            return null;
        }

        Look look_ = new Look();

        look_.setCloset( closetMapper.fromId( lookDTO.getClosetId() ) );

        look_.setId( lookDTO.getId() );

        look_.setUserId( lookDTO.getUserId() );

        look_.setTemperature( lookDTO.getTemperature() );

        look_.setDayTime( lookDTO.getDayTime() );

        look_.setPictureIndex( lookDTO.getPictureIndex() );

        look_.setUrl( lookDTO.getUrl() );

        return look_;
    }

    private Long lookClosetId(Look look) {

        if ( look == null ) {

            return null;
        }

        Closet closet = look.getCloset();

        if ( closet == null ) {

            return null;
        }

        Long id = closet.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

