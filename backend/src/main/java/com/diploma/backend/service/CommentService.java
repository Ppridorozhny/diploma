package com.diploma.backend.service;

import java.util.List;

import com.diploma.backend.model.entities.Comment;

public interface CommentService {

    List<Comment> getAllComments();

    List<Comment> getAllCommentsByTicketId(Integer ticketId);

    Comment getComment(Long id);

    Comment createComment(Comment comment);

    void deleteComment(Long id);

}
