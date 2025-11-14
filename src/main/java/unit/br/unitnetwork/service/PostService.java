package unit.br.unitnetwork.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import unit.br.unitnetwork.dto.PostRequestDto;
import unit.br.unitnetwork.dto.PostResponseDto;
import unit.br.unitnetwork.dto.PostWithUserResponseDto;
import unit.br.unitnetwork.dto.UserResponseDto;
import unit.br.unitnetwork.entity.Post;
import unit.br.unitnetwork.entity.User;
import unit.br.unitnetwork.exception.PostNotFoundException;
import unit.br.unitnetwork.exception.UserNotFound;
import unit.br.unitnetwork.repository.PostRepository;
import unit.br.unitnetwork.repository.UserRepository;
import unit.br.unitnetwork.utils.Strings;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public PostResponseDto register(PostRequestDto post) {
        if (post.getUserId() == null) {
            throw new UserNotFound(String.format(Strings.ID_NOT_FOUND, null));
        }

        User user = userService.toUser(userService.getById(post.getUserId()));
        Post newPost = postRepository.save(toPost(post, user));
        return fromPost(newPost);
    }

    public PostWithUserResponseDto getAllByUserId(Long userId) {
        UserResponseDto user = userService.getById(userId);
        List<PostResponseDto> posts = postRepository.findAllByUserId(userId).stream()
                .map(this::fromPost)
                .toList();

        return PostWithUserResponseDto.builder()
                .user(user)
                .posts(posts)
                .build();
    }

    public PostResponseDto findById(Long id){
      return modelMapper.map(postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post não encontrado")), PostResponseDto.class);
    }

    public Post update(PostRequestDto post, Long postId) {
        Post existingPost = toPost(findById(postId));
        System.out.println(existingPost.toString());
        existingPost.setMessage(post.getMessage());
        return  postRepository.save(existingPost);
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    private Post toPost(PostRequestDto postDto, User user) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);
        return post;
    }

    private Post toPost(PostResponseDto postResponseDto) {
        return modelMapper.map(postResponseDto, Post.class);
    }

    private PostResponseDto fromPost(Post post) {
        return modelMapper.map(post, PostResponseDto.class);
    }


    private PostWithUserResponseDto fromPostToDto(Post post){
        return modelMapper.map(post, PostWithUserResponseDto.class);
    }
    @Cacheable(cacheNames = {"postCache"}, key = "#userId" )
    public List<PostResponseDto> findAllByUserId(Long userId) {
        simulateBackEndCall();
        var posts = postRepository.findAllByUserId(userId);
        return posts.stream()
                .map(p-> fromPost(p))
                .collect(Collectors.toList());
    }

    public void simulateBackEndCall(){
        try{
            System.out.println("---------- Going to Sleep for 5 seconds ------------------");
            Thread.sleep( 5 * 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
