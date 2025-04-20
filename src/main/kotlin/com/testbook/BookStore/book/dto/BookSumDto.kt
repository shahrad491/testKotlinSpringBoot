package com.testbook.BookStore.book.dto

import com.testbook.BookStore.author.dto.AuthorSumDto

class BookSumDto (
    val isbn: String,
    val title: String,
    val description:String,

    val image: String,

    val author: AuthorSumDto
)