package com.testbook.BookStore.domain

import jakarta.persistence.*

@Entity
@Table(name = "books")
data class Book(
    @Id
    @Column(name = "book_id")
    val isbn: String,
    @Column(name = "book_title")
    val title: String,
    @Column(name = "book_description")
    val description:String,

    @Column(name = "book_image")
    val image: String,

    @ManyToOne(cascade = [(CascadeType.DETACH)])
    @JoinColumn(name = "author_id")
    val author: Author) {
}