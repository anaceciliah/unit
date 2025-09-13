package unit.br.unitnetwork.entity;

import jakarta.persistence.*;
import lombok.*;
import unit.br.unitnetwork.utils.Constants;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (length = Constants.GENERAL_MAX_LENGTH, nullable = false)
    private String name;

    @Column (length = Constants.GENERAL_MAX_LENGTH, nullable = false, unique = true)
    private String email;

    @Column (length = Constants.PHOTO_PATH, nullable = true)
    private String photo;

    @Column (length = Constants.MESSAGE_LENGTH, nullable = true)
    private String message;

    @Column
    private boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Post> posts = new HashSet<>();

}
