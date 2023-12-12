package com.myblogp2.Controller;

import com.myblogp2.Service.CommentService;
import com.myblogp2.payload.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestParam("postId") long postId, @RequestBody
    CommentDto commentDto) {
        CommentDto comment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCommentById(@RequestParam("postId") long postId, @RequestParam("commentId") long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("comment is deleted successfully", HttpStatus.OK);
    }

    @GetMapping
    public List<CommentDto> getPostCom(@RequestParam("postId") long postId) {
        List<CommentDto> postComments = commentService.getPostComments(postId);
        return postComments;
    }

    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestParam("commentId") long commentId, @RequestBody CommentDto commentDto) {
        CommentDto upCom=commentService.updateCommentById(commentId, commentDto);
        return new ResponseEntity<>(upCom, HttpStatus.OK);
    }
}
