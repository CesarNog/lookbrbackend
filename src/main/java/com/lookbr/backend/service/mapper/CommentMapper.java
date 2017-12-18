package com.lookbr.backend.service.mapper;

import com.lookbr.backend.domain.*;
import com.lookbr.backend.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {LookMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "look.id", target = "lookId")
    CommentDTO toDto(Comment comment); 

    @Mapping(source = "lookId", target = "look")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
