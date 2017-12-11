package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.Comment;

import com.lookbr.backend.domain.Look;

import com.lookbr.backend.service.dto.CommentDTO;

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

public class CommentMapperImpl implements CommentMapper {

    @Autowired

    private LookMapper lookMapper;

    @Override

    public List<Comment> toEntity(List<CommentDTO> dtoList) {

        if ( dtoList == null ) {

            return null;
        }

        List<Comment> list = new ArrayList<Comment>();

        for ( CommentDTO commentDTO : dtoList ) {

            list.add( toEntity( commentDTO ) );
        }

        return list;
    }

    @Override

    public List<CommentDTO> toDto(List<Comment> entityList) {

        if ( entityList == null ) {

            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>();

        for ( Comment comment : entityList ) {

            list.add( toDto( comment ) );
        }

        return list;
    }

    @Override

    public CommentDTO toDto(Comment comment) {

        if ( comment == null ) {

            return null;
        }

        CommentDTO commentDTO_ = new CommentDTO();

        commentDTO_.setLookId( commentLookId( comment ) );

        commentDTO_.setId( comment.getId() );

        commentDTO_.setComment( comment.getComment() );

        commentDTO_.setConsultantProfilePhoto( comment.getConsultantProfilePhoto() );

        commentDTO_.setConsultantName( comment.getConsultantName() );

        commentDTO_.setDateVoted( comment.getDateVoted() );

        return commentDTO_;
    }

    @Override

    public Comment toEntity(CommentDTO commentDTO) {

        if ( commentDTO == null ) {

            return null;
        }

        Comment comment_ = new Comment();

        comment_.setLook( lookMapper.fromId( commentDTO.getLookId() ) );

        comment_.setId( commentDTO.getId() );

        comment_.setComment( commentDTO.getComment() );

        comment_.setConsultantProfilePhoto( commentDTO.getConsultantProfilePhoto() );

        comment_.setConsultantName( commentDTO.getConsultantName() );

        comment_.setDateVoted( commentDTO.getDateVoted() );

        return comment_;
    }

    private Long commentLookId(Comment comment) {

        if ( comment == null ) {

            return null;
        }

        Look look = comment.getLook();

        if ( look == null ) {

            return null;
        }

        Long id = look.getId();

        if ( id == null ) {

            return null;
        }

        return id;
    }
}

