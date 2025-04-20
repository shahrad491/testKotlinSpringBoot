package com.testbook.BookStore.book.dto

import com.testbook.BookStore.author.dto.AuthorSum


data class BookSum (
    val isbn: String,
    val title: String,
    val description:String,

    val image: String,

    val authorDto: AuthorSum
)