package br.com.challenge.model;

import br.com.challenge.model.Creature;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "battle")
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "battle")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player playerId;

    @OneToOne
    @JoinColumn(name = "enemy_id")
    private Creature enemyId;

    @Column(name = "player_life_points")
    private int playerLifePoints;

    @Column(name = "enemy_life_points")
    private int enemyLifePoints;

    @Column(name = "player_iniciative")
    private int playerIniciative;

    @Column(name = "enemy_iniciative")
    private int enemyIniciative;

    @Column(name = "turn")
    private int turn;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
