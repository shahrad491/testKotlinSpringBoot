package com.testbook.BookStore

import com.testbook.BookStore.author.dto.AuthorUpdateReq
import com.testbook.BookStore.author.dto.AuthorDto
import com.testbook.BookStore.author.dto.AuthorUpdateReqDto
import com.testbook.BookStore.author.model.AuthorEntity

fun testAuthorDtoA(id: Long?=null) = AuthorDto (
        id=id,
        name = "testUser",
        age = 20,
        image = "author-image.jpg",
        description = "testUser",
        )

fun testAuthorEntityA(id: Long?=null) = AuthorEntity (
        id=id,
        name = "testUser",
        age = 20,
        image = "author-image.jpg",
        description = "testUser",
)

fun testAuthorEntityB(id: Long?=null) = AuthorEntity(
        id = id,
        name = "updated author",
        age = 50,
        description = "updated author",
        image = "updatedimage.jpg",
)

fun testAuthorUpdateDtoA(id: Long?=null) = AuthorUpdateReqDto(
        id= 999,
        name= "testUserupdateddd",
        age = 20,
        image = "author-image.jpg",
        description = "testUserupdateddd",
)

fun testAuthorUpdateReqA(id: Long?=null) = AuthorUpdateReq(
        id= 999,
        name= "testUserupdateddd",
        age = 20,
        image = "author-image.jpg",
        description = "testUserupdateddd",
)
