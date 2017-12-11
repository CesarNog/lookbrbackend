package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Closet;

import com.lookbr.backend.service.dto.ClosetDTO;

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

public class ClosetMapperImpl implements ClosetMapper {

    @Override

    public ClosetDTO toDto(Closet entity) {

        if ( entity == null ) {

            return null;
        }

        ClosetDTO closetDTO = new ClosetDTO();

        closetDTO.setId( entity.getId() );

        closetDTO.setPage( entity.getPage() );

        return closetDTO;
    }

    @Override

    public List<Closet> toEntity(List<ClosetDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Closet> list = new ArrayList<Closet>();

        for ( ClosetDTO closetDTO : dtoList ) {

            list.add( toEntity( closetDTO ) );
        }

        return list;
    }

    @Override

    public List<ClosetDTO> toDto(List<Closet> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<ClosetDTO> list = new ArrayList<ClosetDTO>();

        for ( Closet closet : entityList ) {

            list.add( toDto( closet ) );
        }

        return list;
    }

    @Override

    public Closet toEntity(ClosetDTO closetDTO) {

        if ( closetDTO == null ) {

            return null;
        }

        Closet closet_ = new Closet();

        closet_.setId( closetDTO.getId() );

        closet_.setPage( closetDTO.getPage() );

        return closet_;
    }
}

