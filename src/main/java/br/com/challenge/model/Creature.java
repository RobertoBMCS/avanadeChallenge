package br.com.challenge.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "creature")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Creature {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type_creature")
    private String typeCreature;

    @Column(name = "class_type")
    private  String classType;

    @Column(name = "name")
    private  String name;

    @Column(name = "life_points")
    private int lifePoints;

    @Column(name = "strength_points")
    private int strengthPoints;

    @Column(name = "defense_points")
    private int defensePoints;

    @Column(name = "dexterity_points")
    private int dexterityPoints;

    @Column(name = "dice_amount")
    private int diceAmount;

    @Column(name = "dice_face")
    private int diceFace;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
