package com.testbook.BookStore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.testbook.BookStore.author.model.AuthorEntity
import com.testbook.BookStore.author.repo.AuthorRepo
import com.testbook.BookStore.author.service.AuthorService
import com.testbook.BookStore.testAuthorDtoA
import com.testbook.BookStore.testAuthorEntityA
import com.testbook.BookStore.testAuthorUpdateDtoA
import io.mockk.every
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

private const val AUTHORS_BASE_URL = "/v1/authors"

@SpringBootTest
@AutoConfigureMockMvc
class AuthorsControllerTest @Autowired constructor (
    private val mockMvc: MockMvc,
    @MockkBean val authorRepo: AuthorRepo
) {

    @Autowired
    private lateinit var authorService: AuthorService

    val objectMapper = ObjectMapper()

    @BeforeEach
    fun beforeEach() {
        every {
            authorService.create(any())
        } answers {
            firstArg()
        }
    }

    @Test
    fun `test create author saves the author`() {

        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDtoA()
            )
        }
        val expected = AuthorEntity(
            id = null,
            name = "testUser",
            age = 20,
            image = "author-image.jpg",
            description = "testUser",
        )

        verify { authorService.create(expected) }
    }

    @Test
    fun `test create author returns http 201 at successful create`() {

        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDtoA()
            )
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `test create author returns http 400 at illegal argument `() {
        every {
            authorService.create(any())
        } throws (IllegalArgumentException())

        mockMvc.post(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testAuthorDtoA()
            )
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `test that get all Author returns http 200 and empty`() {
        every {
            authorService.list()
        } answers {
            emptyList()
        }

        mockMvc.get(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json("[]") }
        }
    }

    @Test
    fun `test that get all Author returns http 200 and something`() {
        every {
            authorService.list()
        } answers {
            listOf(testAuthorEntityA(1))
        }

        mockMvc.get(AUTHORS_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$[0].id", equalTo(1)) }
            content { jsonPath("$[0].name", equalTo("testUser")) }
            content { jsonPath("$[0].age", equalTo(20)) }
            content { jsonPath("$[0].image", equalTo("author-image.jpg")) }
            content { jsonPath("$[0].description", equalTo("testUser")) }
        }
    }

    @Test
    fun `test that Author is not found in database`() {
        every {
            authorService.getAuthors(any())
        } answers {
            null
        }

        mockMvc.get("${AUTHORS_BASE_URL}/9999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isNotFound() } }
    }

    @Test
    fun `test that Author is found in database`() {
        every { authorService.getAuthors(any()) } answers {
            testAuthorEntityA(id = 1)
        }
        mockMvc.get("${AUTHORS_BASE_URL}/1") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("id", equalTo(1)) }
            content { jsonPath("name", equalTo("testUser")) }
            content { jsonPath("age", equalTo(20)) }
            content { jsonPath("image", equalTo("author-image.jpg")) }
            content { jsonPath("description", equalTo("testUser")) }
        }
    }

    @Test
    fun `test that full update author returns http 200 and it succeeds`() {
        every {
            authorService.fullUpdate(any(), any())
        } answers {
            secondArg()
        }

        mockMvc.put("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorDtoA(id = 999))
        }.andExpect {
            status { isBadRequest() }
            content { jsonPath("id", equalTo(999)) }
            content { jsonPath("name", equalTo("testUser")) }
            content { jsonPath("age", equalTo(20)) }
            content { jsonPath("image", equalTo("author-image.jpg")) }
            content { jsonPath("description", equalTo("testUser")) }
        }

    }




    @Test
    fun `test that throws a illegal state exception on full update author`() {
        every {
            authorService.fullUpdate(any(), any())
        } throws (IllegalStateException())

        mockMvc.put("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorDtoA(id = 999))
        }.andExpect {
            status { isOk() } }
    }

    @Test
    fun `test that part Update return http 400 on IllegalStateException`(){
        every {
            authorService.partUpdate(any(), any())
        }throws (IllegalStateException())

        mockMvc.patch("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorUpdateDtoA(id = 999L))
        }.andExpect { status { isOk() } }
    }

    @Test
    fun `test that part update returns http 200 and updates`(){
        every {
            authorService.partUpdate(any(), any())
        }answers {
            testAuthorEntityA(id = 999)
        }

        mockMvc.patch("${AUTHORS_BASE_URL}/999") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testAuthorUpdateDtoA(id = 999L))
        }.andExpect { status { isOk() } }
    }
}
