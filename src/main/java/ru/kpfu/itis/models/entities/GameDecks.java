package ru.kpfu.itis.models.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "game_decks")
public class GameDecks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "deck_id")
    private Long deckId;
}
