package com.myblogp2.Service;

import com.myblogp2.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto commentDto);

    void deleteComment(long postId, long commentId);

    List<CommentDto> getPostComments(long postId);

    CommentDto updateCommentById(long commentId, CommentDto commentDto);

}
