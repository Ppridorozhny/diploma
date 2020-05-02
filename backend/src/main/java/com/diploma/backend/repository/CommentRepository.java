package com.diploma.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diploma.backend.model.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByTicketId(Integer id);

}
