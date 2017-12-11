package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Inspiration;

import com.lookbr.backend.domain.Look;

import com.lookbr.backend.domain.Temperature;

import com.lookbr.backend.service.dto.TemperatureDTO;

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

public class TemperatureMapperImpl implements TemperatureMapper {

    @Autowired

    private InspirationMapper inspirationMapper;

    @Autowired

    private LookMapper lookMapper;

    @Override

    public List<Temperature> toEntity(List<TemperatureDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Temperature> list = new ArrayList<Temperature>();

        for ( TemperatureDTO temperatureDTO : dtoList ) {

            list.add( toEntity( temperatureDTO ) );
        }

        return list;
    }

    @Override

    public List<TemperatureDTO> toDto(List<Temperature> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<TemperatureDTO> list = new ArrayList<TemperatureDTO>();

        for ( Temperature temperature : entityList ) {

            list.add( toDto( temperature ) );
        }

        return list;
    }

    @Override

    public TemperatureDTO toDto(Temperature temperature) {

        if ( temperature == null ) {

            return null;
        }

        TemperatureDTO temperatureDTO_ = new TemperatureDTO();

        temperatureDTO_.setLookId( temperatureLookId( temperature ) );

        temperatureDTO_.setInspirationId( temperatureInspirationId( temperature ) );

        temperatureDTO_.setId( temperature.getId() );

        temperatureDTO_.setValue( temperature.getValue() );

        temperatureDTO_.setType( temperature.getType() );

        return temperatureDTO_;
    }

    @Override

    public Temperature toEntity(TemperatureDTO temperatureDTO) {

        if ( temperatureDTO == null ) {

            return null;
        }

        Temperature temperature_ = new Temperature();

        temperature_.setLook( lookMapper.fromId( temperatureDTO.getLookId() ) );

        temperature_.setInspiration( inspirationMapper.fromId( temperatureDTO.getInspirationId() ) );

        temperature_.setId( temperatureDTO.getId() );

        temperature_.setValue( temperatureDTO.getValue() );

        temperature_.setType( temperatureDTO.getType() );

        return temperature_;
    }

    private Long temperatureLookId(Temperature temperature) {

        if ( temperature == null ) {

            return null;
        }

        Look look = temperature.getLook();

        if ( look == null ) {

            return null;
        }

        Long id = look.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }

    private Long temperatureInspirationId(Temperature temperature) {

        if ( temperature == null ) {

            return null;
        }

        Inspiration inspiration = temperature.getInspiration();

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

