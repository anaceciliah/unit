package unit.br.unitnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unit.br.unitnetwork.dto.PostRequestDto;
import unit.br.unitnetwork.dto.PostResponseDto;
import unit.br.unitnetwork.entity.Post;
import unit.br.unitnetwork.service.PostService;

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



}
