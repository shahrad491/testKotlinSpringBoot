package com.testbook.BookStore.services.serviceinterface

import com.testbook.BookStore.domain.AuthorUpdateReq
import com.testbook.BookStore.domain.entity.AuthorEntity

interface AuthorServiceInterface {
    fun create(authorEntity: AuthorEntity):AuthorEntity
    fun list():List<AuthorEntity>
    fun getAuthors(id:Long):AuthorEntity?
    fun fullUpdate(id:Long, author:AuthorEntity):AuthorEntity
    fun partUpdate(id:Long, authorUpdate:AuthorUpdateReq): AuthorEntity
    fun delete(id:Long):AuthorEntity
}
