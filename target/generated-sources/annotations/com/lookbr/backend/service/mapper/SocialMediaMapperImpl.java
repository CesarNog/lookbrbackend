package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Consultant;

import com.lookbr.backend.domain.SocialMedia;

import com.lookbr.backend.service.dto.SocialMediaDTO;

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

public class SocialMediaMapperImpl implements SocialMediaMapper {

    @Autowired

    private ConsultantMapper consultantMapper;

    @Override

    public List<SocialMedia> toEntity(List<SocialMediaDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<SocialMedia> list = new ArrayList<SocialMedia>();

        for ( SocialMediaDTO socialMediaDTO : dtoList ) {

            list.add( toEntity( socialMediaDTO ) );
        }

        return list;
    }

    @Override

    public List<SocialMediaDTO> toDto(List<SocialMedia> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<SocialMediaDTO> list = new ArrayList<SocialMediaDTO>();

        for ( SocialMedia socialMedia : entityList ) {

            list.add( toDto( socialMedia ) );
        }

        return list;
    }

    @Override

    public SocialMediaDTO toDto(SocialMedia socialMedia) {

        if ( socialMedia == null ) {

            return null;
        }

        SocialMediaDTO socialMediaDTO_ = new SocialMediaDTO();

        socialMediaDTO_.setConsultantId( socialMediaConsultantId( socialMedia ) );

        socialMediaDTO_.setId( socialMedia.getId() );

        socialMediaDTO_.setType( socialMedia.getType() );

        socialMediaDTO_.setUrl( socialMedia.getUrl() );

        return socialMediaDTO_;
    }

    @Override

    public SocialMedia toEntity(SocialMediaDTO socialMediaDTO) {

        if ( socialMediaDTO == null ) {

            return null;
        }

        SocialMedia socialMedia_ = new SocialMedia();

        socialMedia_.setConsultant( consultantMapper.fromId( socialMediaDTO.getConsultantId() ) );

        socialMedia_.setId( socialMediaDTO.getId() );

        socialMedia_.setType( socialMediaDTO.getType() );

        socialMedia_.setUrl( socialMediaDTO.getUrl() );

        return socialMedia_;
    }

    private Long socialMediaConsultantId(SocialMedia socialMedia) {

        if ( socialMedia == null ) {

            return null;
        }

        Consultant consultant = socialMedia.getConsultant();

        if ( consultant == null ) {

            return null;
        }

        Long id = consultant.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

