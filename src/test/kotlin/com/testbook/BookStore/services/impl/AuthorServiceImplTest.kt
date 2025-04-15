package com.testbook.BookStore.services.impl

import com.testbook.BookStore.repo.AuthorRepo
import com.testbook.BookStore.services.AuthorService
import com.testbook.BookStore.testAuthorEntityA
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class AuthorServiceImplTest @Autowired constructor(
    private val underTest:AuthorService,
    private val authorRepo:AuthorRepo
) {
    @Test
    fun `test that save the Author in Database`(){
        val savedAuthor = underTest.save(testAuthorEntityA())
        assertThat(savedAuthor.id).isNotNull()

        println(savedAuthor.id)


        val reAuthor = authorRepo.findByIdOrNull(savedAuthor.id)
        assertThat(reAuthor).isNotNull()
        assertThat(reAuthor).isEqualTo(testAuthorEntityA(id  = 1 ))
    }
}