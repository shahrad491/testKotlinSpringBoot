package com.testbook.BookStore.book.service

import com.testbook.BookStore.book.dto.BookSum
import com.testbook.BookStore.book.model.BookEntity
import com.testbook.BookStore.author.repo.AuthorRepo
import com.testbook.BookStore.book.repo.BookRepo
import com.testbook.BookStore.book.service.serviceinterface.BookServiceInterface
import com.testbook.BookStore.book.util.toBookEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    val bookRepo: BookRepo,
    val authorRepo: AuthorRepo
) : BookServiceInterface {

    @Transactional
    override fun upsert(isbn: String, book: BookSum): Pair<BookEntity, Boolean> {
        val normalizeBook = book.copy(isbn = isbn)
        val isBook = bookRepo.existsById(isbn)

        val author = authorRepo.findByIdOrNull(normalizeBook.authorDto.id)
        checkNotNull(author)

        val savedBook = bookRepo.save(normalizeBook.toBookEntity(author))
        return Pair(savedBook,!isBook)
    }

    override fun getBooks(): List<BookEntity> {
        return bookRepo.findAll()
    }
}