package com.testbook.BookStore.controllers

import com.testbook.BookStore.domain.dto.AuthorDto
import com.testbook.BookStore.domain.dto.AuthorUpdateReqDto
import com.testbook.BookStore.services.AuthorService
import com.testbook.BookStore.services.serviceinterface.AuthorServiceInterface
import com.testbook.BookStore.toAuthorDto
import com.testbook.BookStore.toAuthorEntity
import com.testbook.BookStore.toAuthorUpdateReq
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
        try {
            val createdOrder = authorServiceInterface.create(authorDto.toAuthorEntity()).toAuthorDto()
            return ResponseEntity<AuthorDto>(createdOrder, HttpStatus.CREATED)
        }catch (ex: IllegalArgumentException){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping()
    fun getAllAuthors(): List<AuthorDto> {
        return authorService.list().map { it.toAuthorDto() }
    }

    @GetMapping(path = ["/{id}"])
    fun getAuthorById(@PathVariable("id") id:Long):ResponseEntity<AuthorDto> {
        val foundAuthor = authorService.getAuthors(id)?.toAuthorDto()
        return foundAuthor?.let{return ResponseEntity(it, HttpStatus.OK)}?:
        ResponseEntity<AuthorDto>(HttpStatus.NOT_FOUND)
    }

    @PutMapping(path = ["/{id}"])
    fun fullUpdateAuthorById(@PathVariable("id") id:Long, @RequestBody author: AuthorDto):ResponseEntity<AuthorDto> {
        return try {
            val updatedAuthor = authorService.fullUpdate(id, author.toAuthorEntity()).toAuthorDto()
            ResponseEntity(updatedAuthor, HttpStatus.OK)
        }catch (ex:IllegalStateException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping(path = ["/{id}"])
    fun partUpdateAuthorById(@PathVariable("id") id:Long, @RequestBody authorUpdateReqDto: AuthorUpdateReqDto):ResponseEntity<AuthorDto> {
        return try {
            val updatedAuthor = authorService.partUpdate(id, authorUpdateReqDto.toAuthorUpdateReq())
            ResponseEntity(updatedAuthor.toAuthorDto(), HttpStatus.OK)
        }catch (ex:IllegalStateException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}