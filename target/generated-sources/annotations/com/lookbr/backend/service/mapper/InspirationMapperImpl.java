package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Inspiration;

import com.lookbr.backend.domain.Timeline;

import com.lookbr.backend.service.dto.InspirationDTO;

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

public class InspirationMapperImpl implements InspirationMapper {

    @Autowired

    private TimelineMapper timelineMapper;

    @Override

    public List<Inspiration> toEntity(List<InspirationDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Inspiration> list = new ArrayList<Inspiration>();

        for ( InspirationDTO inspirationDTO : dtoList ) {

            list.add( toEntity( inspirationDTO ) );
        }

        return list;
    }

    @Override

    public List<InspirationDTO> toDto(List<Inspiration> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<InspirationDTO> list = new ArrayList<InspirationDTO>();

        for ( Inspiration inspiration : entityList ) {

            list.add( toDto( inspiration ) );
        }

        return list;
    }

    @Override

    public InspirationDTO toDto(Inspiration inspiration) {

        if ( inspiration == null ) {

            return null;
        }

        InspirationDTO inspirationDTO_ = new InspirationDTO();

        inspirationDTO_.setTimelineId( inspirationTimelineId( inspiration ) );

        inspirationDTO_.setId( inspiration.getId() );

        inspirationDTO_.setConsultantName( inspiration.getConsultantName() );

        inspirationDTO_.setConsultantProfilePhotoURL( inspiration.getConsultantProfilePhotoURL() );

        inspirationDTO_.setInspirationURL( inspiration.getInspirationURL() );

        inspirationDTO_.setPage( inspiration.getPage() );

        return inspirationDTO_;
    }

    @Override

    public Inspiration toEntity(InspirationDTO inspirationDTO) {

        if ( inspirationDTO == null ) {

            return null;
        }

        Inspiration inspiration_ = new Inspiration();

        inspiration_.setTimeline( timelineMapper.fromId( inspirationDTO.getTimelineId() ) );

        inspiration_.setId( inspirationDTO.getId() );

        inspiration_.setConsultantName( inspirationDTO.getConsultantName() );

        inspiration_.setConsultantProfilePhotoURL( inspirationDTO.getConsultantProfilePhotoURL() );

        inspiration_.setInspirationURL( inspirationDTO.getInspirationURL() );

        inspiration_.setPage( inspirationDTO.getPage() );

        return inspiration_;
    }

    private Long inspirationTimelineId(Inspiration inspiration) {

        if ( inspiration == null ) {

            return null;
        }

        Timeline timeline = inspiration.getTimeline();

        if ( timeline == null ) {

            return null;
        }

        Long id = timeline.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

