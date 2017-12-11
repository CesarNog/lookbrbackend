package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Login;

import com.lookbr.backend.domain.User;

import com.lookbr.backend.service.dto.LoginDTO;

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

public class LoginMapperImpl implements LoginMapper {

    @Autowired

    private UserMapper userMapper;

    @Override

    public List<Login> toEntity(List<LoginDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Login> list = new ArrayList<Login>();

        for ( LoginDTO loginDTO : dtoList ) {

            list.add( toEntity( loginDTO ) );
        }

        return list;
    }

    @Override

    public List<LoginDTO> toDto(List<Login> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<LoginDTO> list = new ArrayList<LoginDTO>();

        for ( Login login : entityList ) {

            list.add( toDto( login ) );
        }

        return list;
    }

    @Override

    public LoginDTO toDto(Login login) {

        if ( login == null ) {

            return null;
        }

        LoginDTO loginDTO_ = new LoginDTO();

        loginDTO_.setUserId( loginUserId( login ) );

        loginDTO_.setId( login.getId() );

        loginDTO_.setLoginType( login.getLoginType() );

        loginDTO_.setToken( login.getToken() );

        return loginDTO_;
    }

    @Override

    public Login toEntity(LoginDTO loginDTO) {

        if ( loginDTO == null ) {

            return null;
        }

        Login login_ = new Login();

        login_.setUser( userMapper.userFromId( loginDTO.getUserId() ) );

        login_.setId( loginDTO.getId() );

        login_.setLoginType( loginDTO.getLoginType() );

        login_.setToken( loginDTO.getToken() );

        return login_;
    }

    private Long loginUserId(Login login) {

        if ( login == null ) {

            return null;
        }

        User user = login.getUser();

        if ( user == null ) {

            return null;
        }

        Long id = user.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

