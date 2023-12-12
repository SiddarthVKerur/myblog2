package com.myblogp2.Service.Impl;

import com.myblogp2.Entity.Comment;
import com.myblogp2.Entity.Post;
import com.myblogp2.Repository.CommentRepository;
import com.myblogp2.Repository.PostRepository;
import com.myblogp2.Service.CommentService;
import com.myblogp2.exception.ResourceNotFound;
import com.myblogp2.payload.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepo;
    private CommentRepository commentRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo,ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id: " + postId)
        );
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return mapToDto(savedComment);

    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id: " + postId)
        );
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getPostComments(long postId) {
        List<Comment> postComEntObjList = commentRepo.getCommentsByPostId(postId);
        List<CommentDto>postComDtoObjList=postComEntObjList.stream().map(i->mapToDto(i)).collect(Collectors.toList());
        return postComDtoObjList;
    }

    @Override
    public CommentDto updateCommentById(long commentId, CommentDto commentDto) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFound("comment not found with id: " + commentId));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment upCom = commentRepo.save(comment);
        return mapToDto(upCom);
    }

    Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);

        /*Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());*/
        return comment;
    }

    CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        /*CommentDto commentDto = new CommentDto();
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());*/
        return commentDto;
    }
}
