package com.testbook.BookStore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.testbook.BookStore.domain.entity.AuthorEntity
import com.testbook.BookStore.repo.AuthorRepo
import com.testbook.BookStore.services.AuthorService
import com.testbook.BookStore.services.serviceinterface.AuthorServiceInterface
import com.testbook.BookStore.testAuthorDtoA
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AuthorsControllerTest @Autowired constructor (
    private val mockMvc: MockMvc,
    @MockkBean val authorRepo: AuthorRepo
){

    @Autowired
    private lateinit var authorService: AuthorService

    @Autowired
    private lateinit var authorServiceInterface: AuthorServiceInterface
    val objectMapper = ObjectMapper()

    @BeforeEach
    fun beforeEach(){
            every {
                authorService.save(any())
            }answers {
                firstArg()
            }
    }

    @Test
    fun `test create author saves the author`() {

        mockMvc.post("/v1/authors"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDtoA()
            )
        }
        val expected= AuthorEntity(
            id=null,
            name = "testUser",
            age = 20,
            image = "author-image.jpg",
            description = "testUser",
            )

        verify{authorService.save(expected)}
    }

    @Test
    fun `test create author returns http 201 at successful create`() {

        mockMvc.post("/v1/authors"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDtoA()
            )
        }.andExpect{
            status{isCreated()}
        }
    }
}