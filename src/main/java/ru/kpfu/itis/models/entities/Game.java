package ru.kpfu.itis.models.entities;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "game_decks",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "deck_id")
    )
    private List<Deck> decks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private List<Currency> currencies;

    @ManyToMany
    @JoinTable(
            name = "user_games",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Fetch(value = FetchMode.JOIN)
    private List<User> user;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "play_ground")
    private String playGround;
}
