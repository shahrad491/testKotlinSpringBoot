package com.testbook.BookStore.book.service.serviceinterface

import com.testbook.BookStore.book.dto.BookDto
import com.testbook.BookStore.book.dto.BookSum
import com.testbook.BookStore.book.model.BookEntity

interface BookServiceInterface {
    fun upsert(isbn: String, book: BookSum): Pair<BookEntity, Boolean>
    fun getBooks(authorId:Long?): List<BookEntity>
}