package com.myblogp2.Repository;

import com.myblogp2.Entity.Comment;
import com.myblogp2.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> getCommentsByPostId(long postId);
}
