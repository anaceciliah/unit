package unit.br.unitnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unit.br.unitnetwork.dto.PostRequestDto;
import unit.br.unitnetwork.dto.PostResponseDto;
import unit.br.unitnetwork.dto.PostWithUserResponseDto;
import unit.br.unitnetwork.entity.Post;
import unit.br.unitnetwork.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> register(@RequestBody PostRequestDto post ) {
        return new ResponseEntity<>(postService.register(post), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PostWithUserResponseDto> findAllByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(postService.getAllByUserId(userId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Post> update(@RequestBody PostRequestDto post, @RequestParam Long postId) {
        return new ResponseEntity<>(postService.update(post, postId), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long postId) {
        postService.delete(postId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}
