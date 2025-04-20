package com.testbook.BookStore.book.repo

import com.testbook.BookStore.book.model.BookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepo: JpaRepository<BookEntity, String> {
}