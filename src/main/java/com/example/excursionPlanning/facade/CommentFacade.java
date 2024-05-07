package com.example.excursionPlanning.facade;

import com.example.excursionPlanning.dto.CommentDTO;
import com.example.excursionPlanning.entity.Comment;
import com.example.excursionPlanning.entity.ImageModel;
import com.example.excursionPlanning.payload.web.ImageModelResponse;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CommentFacade {

    public static CommentDTO convertCommentToCommentDTO(Comment comment) {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setMessage(comment.getMessage());
        commentDTO.setUserLogin(comment.getUserLogin());
        commentDTO.setMonument(comment.getMonument());
        commentDTO.setCreateDate(comment.getCreateDate());
        commentDTO.setUserId(comment.getUserId());
        return commentDTO;
    }

}
