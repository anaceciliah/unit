package unit.br.unitnetwork.entity;

import jakarta.persistence.*;
import lombok.*;
import unit.br.unitnetwork.utils.Constants;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = Constants.MESSAGE_LENGTH, nullable = false )
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn (name= "user_id", nullable = false)
    private User user;
}
