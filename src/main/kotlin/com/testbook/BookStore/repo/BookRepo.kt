package com.testbook.BookStore.repo

import com.testbook.BookStore.domain.entity.BookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepo: JpaRepository<BookEntity, String> {
}