package com.testbook.BookStore.services

import com.testbook.BookStore.domain.AuthorUpdateReq
import com.testbook.BookStore.domain.entity.AuthorEntity
import com.testbook.BookStore.repo.AuthorRepo
import com.testbook.BookStore.services.serviceinterface.AuthorServiceInterface
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorService(private val authorRepo: AuthorRepo): AuthorServiceInterface {
    override fun create(authorEntity: AuthorEntity): AuthorEntity {
        require(authorEntity.id == null)
        return authorRepo.save(authorEntity)
    }

    override fun list(): List<AuthorEntity> {
        return authorRepo.findAll()
    }

    override fun getAuthors(id:Long): AuthorEntity?{
        return authorRepo.findByIdOrNull(id)
    }

    @Transactional
    override fun fullUpdate(id: Long, author: AuthorEntity): AuthorEntity {
        check(authorRepo.existsById(id))
        val normalize = author.copy(id = id)
        return authorRepo.save(normalize)
    }

    @Transactional
    override fun partUpdate(id: Long, authorUpdate: AuthorUpdateReq): AuthorEntity {
        val existingAuthor = authorRepo.findByIdOrNull(id)
        checkNotNull(existingAuthor)

        val updatedAuthor = existingAuthor.copy(
            name = authorUpdate.name ?: existingAuthor.name,
            age = authorUpdate.age ?: existingAuthor.age,
            description = authorUpdate.description ?: existingAuthor.description,
            image = authorUpdate.image ?: existingAuthor.image,
        )

        return authorRepo.save(updatedAuthor)
    }

    override fun delete(id: Long): AuthorEntity {
        val existingAuthor = authorRepo.findByIdOrNull(id)
        checkNotNull(existingAuthor)
        authorRepo.deleteById(id)
        return existingAuthor
    }
}