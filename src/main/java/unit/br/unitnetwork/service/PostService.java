package unit.br.unitnetwork.service;

import org.springframework.stereotype.Service;
import unit.br.unitnetwork.dto.PostRequestDto;
import unit.br.unitnetwork.dto.PostResponseDto;
import unit.br.unitnetwork.entity.Post;
import unit.br.unitnetwork.entity.User;
import unit.br.unitnetwork.repository.PostRepository;
import unit.br.unitnetwork.repository.UserRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private Post toPost(PostRequestDto post, User user) {
        return Post.builder()
                .user(user)
                .message(post.getMessage())
                .build();

    }
    private PostResponseDto fromPost(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .message(post.getMessage())
                .build();

    }

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponseDto register(PostRequestDto post) {
        var user = userRepository.findById(post.getUserid()).get();
        var newPost = postRepository.save(toPost(post, user));
        return fromPost(newPost);
    }
}
