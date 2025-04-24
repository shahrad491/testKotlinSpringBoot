package com.testbook.BookStore.book.service.serviceinterface

import com.testbook.BookStore.book.dto.BookSum
import com.testbook.BookStore.book.dto.BookSumDto
import com.testbook.BookStore.book.dto.BookUpdateReq
import com.testbook.BookStore.book.model.BookEntity

interface BookServiceInterface {
    fun upsert(isbn: String, book: BookSum): Pair<BookEntity, Boolean>
    fun getBooks(authorId:Long?): List<BookEntity>
    fun getBook(isbn:String): BookEntity?
    fun partUpdateBook(isbn: String,bookUpdateReq: BookUpdateReq ):BookEntity
    fun deleteBook(isbn: String):BookEntity?
}