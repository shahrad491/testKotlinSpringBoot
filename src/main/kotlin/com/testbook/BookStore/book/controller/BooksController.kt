package com.testbook.BookStore.book.controller

import com.testbook.BookStore.book.dto.BookDto
import com.testbook.BookStore.book.dto.BookSumDto
import com.testbook.BookStore.exceptions.InvaildAuthorException
import com.testbook.BookStore.book.service.BookService
import com.testbook.BookStore.book.util.toBookSum
import com.testbook.BookStore.book.util.toBookSumDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/books")
class BooksController(private val bookService: BookService) {

    @PutMapping(path = ["/{isbn}"])
    fun upsertBook(@PathVariable("isbn") isbn: String,@RequestBody book: BookSumDto): ResponseEntity<BookSumDto> {
        return try{
            val (savedBook,isCreated) =bookService.upsert(isbn,book.toBookSum())
            val resCode = if(isCreated)  HttpStatus.CREATED else HttpStatus.OK

            ResponseEntity(savedBook.toBookSumDto(),resCode)
        }catch (ex: InvaildAuthorException){
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (ex: IllegalStateException){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }


    }

    @GetMapping()
    fun getBooks():List<BookSumDto>{
        return bookService.getBooks().map { it.toBookSumDto() }
    }


}