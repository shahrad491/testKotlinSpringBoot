package com.testbook.BookStore.services.serviceinterface

import com.testbook.BookStore.domain.entity.AuthorEntity

interface AuthorServiceInterface {
    fun save(authorEntity: AuthorEntity):AuthorEntity
    fun list():List<AuthorEntity>
}
