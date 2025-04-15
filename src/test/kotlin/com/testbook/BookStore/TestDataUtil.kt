package com.testbook.BookStore

import com.testbook.BookStore.domain.dto.AuthorDto
import com.testbook.BookStore.domain.entity.AuthorEntity

fun testAuthorDtoA(id: Long?=null) =AuthorDto (
        id=null,
        name = "testUser",
        age = 20,
        image = "author-image.jpg",
        description = "testUser",
        )

fun testAuthorEntityA(id: Long?=null) =AuthorEntity (
        id=null,
        name = "testUser",
        age = 20,
        image = "author-image.jpg",
        description = "testUser",
)