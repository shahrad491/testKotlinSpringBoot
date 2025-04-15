package com.testbook.BookStore.repo

import com.testbook.BookStore.domain.entity.AuthorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepo : JpaRepository<AuthorEntity,Long?>{
}