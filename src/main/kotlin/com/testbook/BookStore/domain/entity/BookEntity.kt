package com.testbook.BookStore.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "books")
data class BookEntity(
    @Id
    @Column(name = "id")
    val isbn: String,
    @Column(name = "title")
    val title: String,
    @Column(name = "description")
    val description:String,

    @Column(name = "image")
    val image: String,

    @ManyToOne(cascade = [(CascadeType.DETACH)])
    @JoinColumn(name = "author_id")
    val authorEntity: AuthorEntity
) {
}