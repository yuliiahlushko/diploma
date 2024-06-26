package com.example.excursionPlanning.services.implementations;

import com.example.excursionPlanning.dao.CommentRepository;
import com.example.excursionPlanning.dao.UserRepository;
import com.example.excursionPlanning.dto.CommentDTO;
import com.example.excursionPlanning.entity.Comment;
import com.example.excursionPlanning.services.interfaces.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;

    private  final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment createComment(CommentDTO commentDTO, Principal principal) {
        Comment comment = new Comment();

        comment.setUserId(userRepository.getUserByEmail(principal.getName()).get().getId());
        comment.setUserLogin(commentDTO.getUserLogin());
        comment.setMessage(commentDTO.getMessage());
        comment.setMonument(commentDTO.getMonument());

        Comment savedComment = null;

        try {
            savedComment = commentRepository.save(comment);
        } catch (Exception e) {
            LOG.error("Comment {} cannot be added!", e.getMessage());
        }
        LOG.info("New comment {} was added", savedComment);
        return savedComment;
    }

    @Override
    public void deleteComment(Long id, Principal principal) {
        Comment comment = commentRepository.getCommentById(id)
                .orElseThrow(() -> new RuntimeException("Comment not exist"));
        try {
            commentRepository.delete(comment);
        } catch (Exception e) {
            LOG.error("Comment {} cannot be deleted!", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(Long id, Principal principal) {
        return commentRepository.getCommentById(id);
    }

    @Override
    public Optional<Comment> putComment(CommentDTO commentDTO, Principal principal) {
        Optional<Comment> comment = commentRepository.getCommentById(commentDTO.getId());

        if (comment.isPresent()) {
            comment.get().setUserId(commentDTO.getUserId());
            comment.get().setUserLogin(commentDTO.getUserLogin());
            comment.get().setMessage(commentDTO.getMessage());
            comment.get().setMonument(commentDTO.getMonument());

            Comment savedComment = null;
            try {
                savedComment = commentRepository.save(comment.get());
                return Optional.of(savedComment);
            } catch (Exception e) {
                LOG.error("Comment {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    public Optional<Comment> patchComment(CommentDTO commentDTO, Principal principal) {
        Optional<Comment> comment = commentRepository.getCommentById(commentDTO.getId());

        if (comment.isPresent()) {
            if (commentDTO.getUserId() != null)
                comment.get().setUserId(commentDTO.getUserId());
            if (commentDTO.getUserLogin() != null)
                comment.get().setUserLogin(commentDTO.getUserLogin());
            if (commentDTO.getMessage() != null)
                comment.get().setMessage(commentDTO.getMessage());
            if (commentDTO.getMonument() != null)
                comment.get().setMonument(commentDTO.getMonument());

            Comment savedComment = null;
            try {
                savedComment = commentRepository.save(comment.get());
                return Optional.of(savedComment);
            } catch (Exception e) {
                LOG.error("Comment {} cannot be updated!", e.getMessage());
            }

        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByMonumentId(Long monumentId) {
        return commentRepository.getAllCommentsByMonumentId(monumentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Comment> getAllCommentsByMonumentId(Long monumentId, Pageable pageable) {
        return commentRepository.getAllCommentsByMonumentId(monumentId, pageable);
    }
}
