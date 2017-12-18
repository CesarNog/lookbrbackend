package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.CommentService;
import com.lookbr.backend.domain.Comment;
import com.lookbr.backend.repository.CommentRepository;
import com.lookbr.backend.service.dto.CommentDTO;
import com.lookbr.backend.service.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Comment.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        log.debug("Request to save Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    /**
     * Get all the comments.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findAll() {
        log.debug("Request to get all Comments");
        return commentRepository.findAll().stream()
            .map(commentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one comment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CommentDTO findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        Comment comment = commentRepository.findOne(id);
        return commentMapper.toDto(comment);
    }

    /**
     * Delete the comment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.delete(id);
    }
}
