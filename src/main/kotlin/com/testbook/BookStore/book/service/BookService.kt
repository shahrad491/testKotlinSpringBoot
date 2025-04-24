package com.testbook.BookStore.book.service

import com.testbook.BookStore.book.dto.BookSum
import com.testbook.BookStore.book.model.BookEntity
import com.testbook.BookStore.author.repo.AuthorRepo
import com.testbook.BookStore.book.dto.BookUpdateReq
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

    override fun getBooks(authorId:Long?): List<BookEntity> {
        return authorId?.let {
            bookRepo.findByAuthorEntityId(it)
        }?: bookRepo.findAll()
    }

    override fun getBook(isbn: String): BookEntity? {
        return bookRepo.findByIdOrNull(isbn)
    }

    override fun partUpdateBook(isbn: String, bookUpdateReq: BookUpdateReq): BookEntity {
        val existBook = bookRepo.findByIdOrNull(isbn)
        checkNotNull(existBook)

        val updateBook = existBook.copy(
            title = bookUpdateReq.title?: existBook.title,
            description = bookUpdateReq.description?: existBook.description,
            image = bookUpdateReq.image?: existBook.image,
        )

        return bookRepo.save(updateBook)
    }

    override fun deleteBook(isbn: String): BookEntity? {
        val existingBook = bookRepo.findByIdOrNull(isbn)
        checkNotNull(existingBook)

        bookRepo.deleteById(existingBook.isbn)
        return existingBook
    }
}