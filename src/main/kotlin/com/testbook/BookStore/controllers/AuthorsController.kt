package com.testbook.BookStore.controllers

import com.testbook.BookStore.domain.dto.AuthorDto
import com.testbook.BookStore.services.AuthorService
import com.testbook.BookStore.services.serviceinterface.AuthorServiceInterface
import com.testbook.BookStore.toAuthorDto
import com.testbook.BookStore.toAuthorEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/authors"])
class AuthorsController(
    private val authorServiceInterface: AuthorServiceInterface,
    private val authorService: AuthorService
) {

    @PostMapping()
    fun createAuthor(@RequestBody authorDto: AuthorDto):ResponseEntity<AuthorDto> {
        val createdOrder = authorServiceInterface.save(authorDto.toAuthorEntity()).toAuthorDto()
        return ResponseEntity<AuthorDto>(createdOrder, HttpStatus.CREATED)
    }

    @GetMapping()
    fun getAllAuthors(): List<AuthorDto> {
        return authorService.list().map { it.toAuthorDto() }
    }
}