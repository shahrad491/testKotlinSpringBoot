package com.testbook.BookStore.services

import com.testbook.BookStore.domain.entity.AuthorEntity
import com.testbook.BookStore.repo.AuthorRepo
import com.testbook.BookStore.testAuthorEntityA
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class AuthorServiceTest @Autowired constructor(
    private val underTest:AuthorService,
    private val authorRepo:AuthorRepo
) {
    @Autowired
    private lateinit var authorService: AuthorService

    @BeforeEach
    fun setUp() {
        authorRepo.deleteAll()
    }



    @Test
    fun `test that save the Author in Database`(){
        val savedAuthor = underTest.save(testAuthorEntityA())
        assertThat(savedAuthor.id).isNotNull()

        val reAuthor = authorRepo.findByIdOrNull(savedAuthor.id)
        assertThat(reAuthor).isNotNull()
        assertThat(reAuthor).isEqualTo(testAuthorEntityA(id  = savedAuthor.id ))
    }

    @Test
    fun `test that list returns empty when there is no author `(){
        val res = underTest.list()
        assertThat(res).isEmpty()
    }

    @Test
    fun `test that list returns the present authors`(){
        val savedAuth =  authorRepo.save(testAuthorEntityA())
        val expectedAuthors = listOf(savedAuth)
        val res = underTest.list()

        assertThat(res).isEqualTo(expectedAuthors)
    }

    @Test
    fun `test that get returns null when there is no author by the id`(){
        val res = underTest.getAuthors(id =9999)
        assertThat(res).isNull()
    }

    @Test
    fun `test that get returns a author by the id` (){
        val savedAuthor = authorRepo.save(testAuthorEntityA())
        val res = underTest.getAuthors(savedAuthor.id!!)
        assertThat(res).isEqualTo(savedAuthor)
    }
}