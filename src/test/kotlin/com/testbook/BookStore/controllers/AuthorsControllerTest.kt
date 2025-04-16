package com.testbook.BookStore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.testbook.BookStore.domain.entity.AuthorEntity
import com.testbook.BookStore.repo.AuthorRepo
import com.testbook.BookStore.services.AuthorService
import com.testbook.BookStore.services.serviceinterface.AuthorServiceInterface
import com.testbook.BookStore.testAuthorDtoA
import com.testbook.BookStore.testAuthorEntityA
import io.mockk.every
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

private const val AUTHORS_BASE_URL = "/v1/authors"

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

        mockMvc.post(AUTHORS_BASE_URL){
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

        mockMvc.post(AUTHORS_BASE_URL){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDtoA()
            )
        }.andExpect{
            status{isCreated()}
        }
    }

    @Test
    fun `test that get all Author returns http 200 and empty`(){
        every {
            authorService.list()
        }answers {
            emptyList()
        }

        mockMvc.get(AUTHORS_BASE_URL){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status{isOk()}
            content { json("[]") }
        }
    }

    @Test
    fun `test that get all Author returns http 200 and something` (){
        every {
            authorService.list()
        }answers {
            listOf(testAuthorEntityA(1))
        }

        mockMvc.get(AUTHORS_BASE_URL){
            contentType= MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status{isOk()}
            content { jsonPath("$[0].id", equalTo(1))}
            content { jsonPath("$[0].name", equalTo("testUser"))}
            content { jsonPath("$[0].age", equalTo(20))}
            content { jsonPath("$[0].image", equalTo("author-image.jpg"))}
            content { jsonPath("$[0].description", equalTo("testUser"))}
        }
    }

    @Test
    fun `test that Author is not found in database`(){
        every {
            authorService.getAuthors(any())
        }answers {
            null
        }

        mockMvc.get("${AUTHORS_BASE_URL}/9999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {  status{isNotFound()} }
    }

    @Test
    fun `test that Author is found in database`(){
        every {authorService.getAuthors(any())}answers {
            testAuthorEntityA(id=1)
        }
        mockMvc.get("${AUTHORS_BASE_URL}/1"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status{isOk()}
            content { jsonPath("id", equalTo(1))}
            content { jsonPath("name", equalTo("testUser"))}
            content { jsonPath("age", equalTo(20))}
            content { jsonPath("image", equalTo("author-image.jpg"))}
            content { jsonPath("description", equalTo("testUser"))}
        }
    }
}