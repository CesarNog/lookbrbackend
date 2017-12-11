package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Signup;

import com.lookbr.backend.service.dto.SignupDTO;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2017-12-06T15:37:22-0200",

    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"

)

@Component

public class SignupMapperImpl implements SignupMapper {

    @Override

    public Signup toEntity(SignupDTO dto) {

        if ( dto == null ) {

            return null;
        }

        Signup signup = new Signup();

        signup.setId( dto.getId() );

        signup.setEmail( dto.getEmail() );

        signup.setLoginType( dto.getLoginType() );

        signup.setPassword( dto.getPassword() );

        signup.setProfilePhotoUrl( dto.getProfilePhotoUrl() );

        signup.setProfilePhoto( dto.getProfilePhoto() );

        signup.setUsername( dto.getUsername() );

        signup.setToken( dto.getToken() );

        return signup;
    }

    @Override

    public SignupDTO toDto(Signup entity) {

        if ( entity == null ) {

            return null;
        }

        SignupDTO signupDTO = new SignupDTO();

        signupDTO.setId( entity.getId() );

        signupDTO.setEmail( entity.getEmail() );

        signupDTO.setLoginType( entity.getLoginType() );

        signupDTO.setPassword( entity.getPassword() );

        signupDTO.setProfilePhotoUrl( entity.getProfilePhotoUrl() );

        signupDTO.setProfilePhoto( entity.getProfilePhoto() );

        signupDTO.setUsername( entity.getUsername() );

        signupDTO.setToken( entity.getToken() );

        return signupDTO;
    }

    @Override

    public List<Signup> toEntity(List<SignupDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Signup> list = new ArrayList<Signup>();

        for ( SignupDTO signupDTO : dtoList ) {

            list.add( toEntity( signupDTO ) );
        }

        return list;
    }

    @Override

    public List<SignupDTO> toDto(List<Signup> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<SignupDTO> list = new ArrayList<SignupDTO>();

        for ( Signup signup : entityList ) {

            list.add( toDto( signup ) );
        }

        return list;
    }
}

