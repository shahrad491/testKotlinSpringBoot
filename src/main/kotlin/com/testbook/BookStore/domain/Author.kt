package com.testbook.BookStore.domain

import jakarta.persistence.*

@Entity
@Table(name = "authors")
data class Author(
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    val id: Long?,
    @Column(name = "author_name")
    val name: String,
    @Column(name = "author_age")
    val age: Int,
    @Column(name = "author_description")
    val description: String,
    @Column(name = "author_image")
    val image: String){


}