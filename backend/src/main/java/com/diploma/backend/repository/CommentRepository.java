package com.diploma.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diploma.backend.model.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
