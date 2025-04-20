package com.testbook.BookStore.author.repo

import com.testbook.BookStore.author.model.AuthorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepo : JpaRepository<AuthorEntity,Long?>{
}