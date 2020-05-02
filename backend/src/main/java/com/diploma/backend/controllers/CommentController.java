package com.diploma.backend.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diploma.backend.AppConstants;
import com.diploma.backend.model.dto.CommentDTO;
import com.diploma.backend.model.dto.TicketDTO;
import com.diploma.backend.model.entities.Comment;
import com.diploma.backend.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"CommentController"})
@Validated
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper mapper;

    @GetMapping
    public List<CommentDTO> getAllComments() {
        return mapper.map(commentService.getAllComments(), AppConstants.COMMENT_LIST_TYPE);
    }

    @GetMapping("/ticket/{ticketId}")
    public List<TicketDTO> getAllCommentByTicketId(@PathVariable Integer ticketId) {
        return mapper.map(commentService.getAllCommentsByTicketId(ticketId),
                AppConstants.COMMENT_LIST_TYPE);
    }

    @GetMapping("/{id}")
    public CommentDTO getCommentById(@PathVariable Long id) {
        return mapper.map(commentService.getComment(id), CommentDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@RequestBody @Validated CommentDTO commentDTO) {
        Comment comment = mapper.map(commentDTO, Comment.class);
        comment = commentService.createComment(comment);
        return mapper.map(comment, CommentDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

}
