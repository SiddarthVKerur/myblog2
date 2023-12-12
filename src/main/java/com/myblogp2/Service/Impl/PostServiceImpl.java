package com.myblogp2.Service.Impl;

import com.myblogp2.Entity.Post;
import com.myblogp2.Repository.PostRepository;
import com.myblogp2.Service.PostService;
import com.myblogp2.exception.ResourceNotFound;
import com.myblogp2.payload.PostDto;
import com.myblogp2.payload.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public void deletePostById(long id) {
        postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + id)
        );
        postRepository.deleteById(id);

    }

    @Override
    public PostDto findPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("post not found with id: " + id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("post not found with id: " + id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postObjectPage = postRepository.findAll(pageable);
        List<Post> postList = postObjectPage.getContent();
        List<PostDto> dtoList = postList.stream().map(i -> mapToDto(i)).collect(Collectors.toList());
        PostResponse response = new PostResponse();
        response.setDto(dtoList);
        response.setPageNo(postObjectPage.getNumber());
        response.setLastPage(postObjectPage.isLast());
        response.setPageSize(postObjectPage.getSize());
        response.setTotalPages(postObjectPage.getTotalPages());
        return response;
    }

    public Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    public PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        return postDto;

    }
}
