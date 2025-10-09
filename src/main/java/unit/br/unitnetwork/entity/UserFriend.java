package unit.br.unitnetwork.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Entity
@Table(name = "user_friend")
@AllArgsConstructor
@NoArgsConstructor
public class UserFriend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "friend_id", nullable = false)
    private User user;


}
