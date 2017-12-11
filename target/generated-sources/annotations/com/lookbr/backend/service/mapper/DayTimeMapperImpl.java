package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.DayTime;

import com.lookbr.backend.domain.Inspiration;

import com.lookbr.backend.service.dto.DayTimeDTO;

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

public class DayTimeMapperImpl implements DayTimeMapper {

    @Autowired

    private InspirationMapper inspirationMapper;

    @Override

    public List<DayTime> toEntity(List<DayTimeDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<DayTime> list = new ArrayList<DayTime>();

        for ( DayTimeDTO dayTimeDTO : dtoList ) {

            list.add( toEntity( dayTimeDTO ) );
        }

        return list;
    }

    @Override

    public List<DayTimeDTO> toDto(List<DayTime> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<DayTimeDTO> list = new ArrayList<DayTimeDTO>();

        for ( DayTime dayTime : entityList ) {

            list.add( toDto( dayTime ) );
        }

        return list;
    }

    @Override

    public DayTimeDTO toDto(DayTime dayTime) {

        if ( dayTime == null ) {

            return null;
        }

        DayTimeDTO dayTimeDTO_ = new DayTimeDTO();

        dayTimeDTO_.setInspirationId( dayTimeInspirationId( dayTime ) );

        dayTimeDTO_.setId( dayTime.getId() );

        dayTimeDTO_.setValue( dayTime.getValue() );

        dayTimeDTO_.setType( dayTime.getType() );

        return dayTimeDTO_;
    }

    @Override

    public DayTime toEntity(DayTimeDTO dayTimeDTO) {

        if ( dayTimeDTO == null ) {

            return null;
        }

        DayTime dayTime_ = new DayTime();

        dayTime_.setInspiration( inspirationMapper.fromId( dayTimeDTO.getInspirationId() ) );

        dayTime_.setId( dayTimeDTO.getId() );

        dayTime_.setValue( dayTimeDTO.getValue() );

        dayTime_.setType( dayTimeDTO.getType() );

        return dayTime_;
    }

    private Long dayTimeInspirationId(DayTime dayTime) {

        if ( dayTime == null ) {

            return null;
        }

        Inspiration inspiration = dayTime.getInspiration();

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

