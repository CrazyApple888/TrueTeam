package ru.nsu.wallet.entity

import javax.persistence.*

@Entity
@Table(name = "cards")
class Card(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_seq")
    @SequenceGenerator(name = "card_seq", allocationSize = 1)
    val id: Int = 0,

    @ManyToOne
    val owner: User,

    @Column(name = "type")
    val type: String = "",

    @Column(name = "number")
    val number: String = "",

    @Column(name = "name")
    val name: String = ""
)