package ru.kpfu.itis.models.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "currency_name")
    private String name;

    @Column(name = "currency_description")
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
