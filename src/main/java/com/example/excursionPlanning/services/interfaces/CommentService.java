package com.example.excursionPlanning.services.interfaces;

import com.example.excursionPlanning.dto.CommentDTO;
import com.example.excursionPlanning.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {

    Comment createComment(CommentDTO commentDTO, Principal principal);

    void deleteComment(Long id, Principal principal);

    Optional<Comment> getCommentById(Long id, Principal principal);

    Optional<Comment> putComment(Comment commentDTO, Principal principal);

    Optional<Comment> patchComment(Comment commentDTO, Principal principal);

    List<Comment> getAllCommentsByMonumentId(Long monumentId);

    //Pageable
    Page<Comment> getAllCommentsByMonumentId(Long monumentId, Pageable pageable);
}


