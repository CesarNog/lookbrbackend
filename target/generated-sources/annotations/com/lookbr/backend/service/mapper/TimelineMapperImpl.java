package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Timeline;

import com.lookbr.backend.service.dto.TimelineDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-12-06T15:37:21-0200",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"

)

@Component

public class TimelineMapperImpl implements TimelineMapper {

    @Override

    public TimelineDTO toDto(Timeline entity) {

        if ( entity == null ) {

            return null;
        }

        TimelineDTO timelineDTO = new TimelineDTO();

        timelineDTO.setId( entity.getId() );

        timelineDTO.setPage( entity.getPage() );

        return timelineDTO;
    }

    @Override

    public List<Timeline> toEntity(List<TimelineDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Timeline> list = new ArrayList<Timeline>();

        for ( TimelineDTO timelineDTO : dtoList ) {

            list.add( toEntity( timelineDTO ) );
        }

        return list;
    }

    @Override

    public List<TimelineDTO> toDto(List<Timeline> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<TimelineDTO> list = new ArrayList<TimelineDTO>();

        for ( Timeline timeline : entityList ) {

            list.add( toDto( timeline ) );
        }

        return list;
    }

    @Override

    public Timeline toEntity(TimelineDTO timelineDTO) {

        if ( timelineDTO == null ) {

            return null;
        }

        Timeline timeline_ = new Timeline();

        timeline_.setId( timelineDTO.getId() );

        timeline_.setPage( timelineDTO.getPage() );

        return timeline_;
    }
}

