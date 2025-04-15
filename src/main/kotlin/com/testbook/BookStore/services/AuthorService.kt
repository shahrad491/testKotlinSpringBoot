package com.testbook.BookStore.services

import com.testbook.BookStore.domain.entity.AuthorEntity
import com.testbook.BookStore.repo.AuthorRepo
import com.testbook.BookStore.services.serviceinterface.AuthorServiceInterface
import org.springframework.stereotype.Service

@Service
class AuthorService(private val authorRepo: AuthorRepo): AuthorServiceInterface {
    override fun save(authorEntity: AuthorEntity): AuthorEntity {
        return authorRepo.save(authorEntity)
    }

    override fun list(): List<AuthorEntity> {
        return authorRepo.findAll()
    }
}