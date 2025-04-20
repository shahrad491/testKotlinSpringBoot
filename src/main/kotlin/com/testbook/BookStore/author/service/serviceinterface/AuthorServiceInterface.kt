package com.testbook.BookStore.author.service.serviceinterface

import com.testbook.BookStore.author.dto.AuthorUpdateReq
import com.testbook.BookStore.author.model.AuthorEntity

interface AuthorServiceInterface {
    fun create(authorEntity: AuthorEntity): AuthorEntity
    fun list():List<AuthorEntity>
    fun getAuthors(id:Long): AuthorEntity?
    fun fullUpdate(id:Long, author: AuthorEntity): AuthorEntity
    fun partUpdate(id:Long, authorUpdate: AuthorUpdateReq): AuthorEntity
    fun delete(id:Long): AuthorEntity
}
