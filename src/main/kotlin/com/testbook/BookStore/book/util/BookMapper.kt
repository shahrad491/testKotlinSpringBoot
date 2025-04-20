package com.testbook.BookStore.book.util

import com.testbook.BookStore.author.model.AuthorEntity
import com.testbook.BookStore.author.util.toAuthorSum
import com.testbook.BookStore.author.util.toAuthorSumDto
import com.testbook.BookStore.book.dto.BookSum
import com.testbook.BookStore.book.dto.BookSumDto
import com.testbook.BookStore.book.model.BookEntity

class BookMapper {
}

fun BookSum.toBookEntity(author : AuthorEntity) = BookEntity(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    authorEntity = author,
)

fun BookSumDto.toBookSum() = BookSum(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    authorDto = this.author.toAuthorSum()
)

fun BookEntity.toBookSumDto() = BookSumDto(
    isbn = this.isbn,
    title = this.title,
    description = this.description,
    image = this.image,
    author = authorEntity.toAuthorSumDto()
)