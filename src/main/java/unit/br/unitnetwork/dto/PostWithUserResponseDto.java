package unit.br.unitnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostWithUserResponseDto {

    UserResponseDto user;
    List<PostResponseDto> posts;
}
