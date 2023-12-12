package com.myblogp2.Controller;

import com.myblogp2.Service.PostService;
import com.myblogp2.payload.PostDto;
import com.myblogp2.payload.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostRestController {

    final private PostService postService;

    public PostRestController(PostService postService) {

        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto) {
        PostDto dto = postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("post is deleted", HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable long id) {
        PostDto postDto = postService.findPostById(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id, @RequestBody PostDto postDto) {
        PostDto dto = postService.updatePostById(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo, @RequestParam(name = "pageSize", required = false, defaultValue = "3") int pageSize, @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy, @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        PostResponse posts = postService.getPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(posts, HttpStatus.OK);

    }
}
