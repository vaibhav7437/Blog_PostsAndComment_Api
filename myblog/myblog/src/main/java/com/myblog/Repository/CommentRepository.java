package com.myblog.Repository;

import com.myblog.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {



    List<Comment> findCommentsByPostId(long postId);
}
