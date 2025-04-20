package com.testbook.BookStore.book.dto

import com.testbook.BookStore.author.dto.AuthorDto

class BookDto (
    val isbn: String,
    val title: String,
    val description:String,

    val image: String,

    val authorDto: AuthorDto
)