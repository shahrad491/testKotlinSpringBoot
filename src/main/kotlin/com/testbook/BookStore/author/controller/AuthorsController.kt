package com.testbook.BookStore.author.controller

import com.testbook.BookStore.author.dto.AuthorDto
import com.testbook.BookStore.author.dto.AuthorUpdateReqDto
import com.testbook.BookStore.author.service.AuthorService
import com.testbook.BookStore.author.service.serviceinterface.AuthorServiceInterface
import com.testbook.BookStore.author.util.toAuthorDto
import com.testbook.BookStore.author.util.toAuthorEntity
import com.testbook.BookStore.author.util.toAuthorUpdateReq
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
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

    @DeleteMapping(path = ["/{id}"])
    fun deleteAuthorById(@PathVariable("id") id:Long):ResponseEntity<AuthorDto>{
        return try {
            val deletedAuthor = authorService.delete(id)
            ResponseEntity(deletedAuthor.toAuthorDto(),HttpStatus.OK)
        }catch (ex: IllegalStateException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}