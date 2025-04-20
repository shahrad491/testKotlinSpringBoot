package com.testbook.BookStore.author.util

import com.testbook.BookStore.author.dto.*
import com.testbook.BookStore.author.model.AuthorEntity
import com.testbook.BookStore.exceptions.InvaildAuthorException

fun AuthorEntity.toAuthorDto() = AuthorDto(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image,
)

fun AuthorDto.toAuthorEntity() = AuthorEntity(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image,
)

fun AuthorUpdateReqDto.toAuthorUpdateReq()= AuthorUpdateReq(
    id = this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image,
)

fun AuthorSumDto.toAuthorSum() = AuthorSum(
    id = this.id,
    name = this.name,
    image = this.image,
)

fun AuthorEntity.toAuthorSumDto(): AuthorSumDto {
    val authorId = this.id?: throw InvaildAuthorException()
    return AuthorSumDto(
        id = authorId,
        name = this.name,
        image = this.image,
    )
}