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
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "title")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Integer value;

    @Column(name = "uniqueness_token")
    private Integer uniquenessToken;
}
