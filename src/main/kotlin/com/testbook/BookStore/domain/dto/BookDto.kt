package com.testbook.BookStore.domain.dto

import com.testbook.BookStore.domain.dto.AuthorDto

class BookDto (
    val isbn: String,
    val title: String,
    val description:String,

    val image: String,

    val authorDto: AuthorDto
)