package com.testbook.BookStore

import com.testbook.BookStore.domain.dto.AuthorDto
import com.testbook.BookStore.domain.entity.AuthorEntity

fun AuthorEntity.toAuthorDto() = AuthorDto(
        id= this.id,
        name = this.name,
        age = this.age,
        description = this.description,
        image = this.image,
    )

fun AuthorDto.toAuthorEntity() = AuthorEntity(
    id= this.id,
    name = this.name,
    age = this.age,
    description = this.description,
    image = this.image,
)
