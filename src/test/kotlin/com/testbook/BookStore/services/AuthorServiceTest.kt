package com.testbook.BookStore.services

import com.testbook.BookStore.author.dto.AuthorUpdateReq
import com.testbook.BookStore.author.model.AuthorEntity
import com.testbook.BookStore.author.repo.AuthorRepo
import com.testbook.BookStore.testAuthorEntityA
import com.testbook.BookStore.testAuthorEntityB
import com.testbook.BookStore.testAuthorUpdateReqA
import com.testbook.BookStore.author.service.AuthorService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class AuthorServiceTest @Autowired constructor(

    @Autowired
    private val underTest: AuthorService,
    private val authorRepo: AuthorRepo
) {
    @BeforeEach
    fun setUp() {
        authorRepo.deleteAll()
    }

    @Test
    fun `test that Author with id throws an illegal argument exception`() {
        assertThrows<IllegalArgumentException> {
            val existAuthor = testAuthorEntityA(id =999)
            underTest.create(existAuthor)
        }
    }

    @Test
    fun `test that save the Author in Database`(){
        val savedAuthor = underTest.create(testAuthorEntityA())
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

    @Test
    fun `test that fullUpdate on Author is success`(){
        val savedAuthor = authorRepo.save(testAuthorEntityA())
        val userId = savedAuthor.id!!

        val updatedAuthor = testAuthorEntityB(id = userId)
        val res = underTest.fullUpdate(userId, updatedAuthor)
        assertThat(res).isEqualTo(updatedAuthor)

        val gotAuthor =authorRepo.findByIdOrNull(userId)
        assertThat(gotAuthor).isNotNull()
        assertThat(gotAuthor).isEqualTo(updatedAuthor)
    }

    @Test
    fun `test that fullUpdate on Author is failure due to not being in database so its illegalStateException`(){
        assertThrows<IllegalStateException> {
            val nonExistingAuthor =999L
            val updatedAuthor = testAuthorEntityB(id = nonExistingAuthor)
            underTest.fullUpdate(nonExistingAuthor,updatedAuthor)
        }
    }

    @Test
    fun `test that part update author throws an illegalStateException`() {
        assertThrows<IllegalStateException> {
            val nonExistingAuthor =999L
            val updatedAuthor = testAuthorUpdateReqA(id = nonExistingAuthor)
            underTest.partUpdate(nonExistingAuthor,updatedAuthor)
        }
    }

    @Test
    fun `test that part update doesnt update author when there is nothing to update`(){
        val existingAuthor = authorRepo.save(testAuthorEntityA())
        val updatedAuthor = underTest.partUpdate(existingAuthor.id!!, AuthorUpdateReq())
        assertThat(updatedAuthor).isEqualTo(existingAuthor)
    }

    @Test
    fun `test that part update successfully updates the author name`(){
        val newName = "new Name"
        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(name = newName)
        val authorUpdateReq = AuthorUpdateReq(
            name= newName
        )

        assertThatAuthorPartUpdateIsUpdated(
            existAuthor = existingAuthor,
            expected = expectedAuthor,
            authorUpdateReq
        )
    }

    @Test
    fun `test that part update successfully updates the author age`(){
        val newAge = 40
        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(age = newAge)
        val authorUpdateReq = AuthorUpdateReq(
            age= newAge
        )

        assertThatAuthorPartUpdateIsUpdated(
            existAuthor = existingAuthor,
            expected = expectedAuthor,
            authorUpdateReq
        )
    }

    @Test
    fun `test that part update successfully updates the author description`(){
        val newDescription = "new Description is for the test"
        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(description = newDescription)
        val authorUpdateReq = AuthorUpdateReq(
            description = newDescription
        )

        assertThatAuthorPartUpdateIsUpdated(
            existAuthor = existingAuthor,
            expected = expectedAuthor,
            authorUpdateReq
        )
    }

    @Test
    fun `test that part update successfully updates the author image`(){
        val newImage = "newImage4Test.jpeg"
        val existingAuthor = testAuthorEntityA()
        val expectedAuthor = existingAuthor.copy(image = newImage)
        val authorUpdateReq = AuthorUpdateReq(
            image = newImage
        )

        assertThatAuthorPartUpdateIsUpdated(
            existAuthor = existingAuthor,
            expected = expectedAuthor,
            authorUpdateReq
        )
    }

        private fun assertThatAuthorPartUpdateIsUpdated(
            existAuthor: AuthorEntity,
            expected: AuthorEntity,
            authorUpdate: AuthorUpdateReq,
    ){

        val saveExistAuthor = authorRepo.save(existAuthor)
        val authorId= saveExistAuthor.id!!

        val updatedAuthor = underTest.partUpdate(existAuthor.id!!, authorUpdate)

        val expected = expected.copy(id = authorId)
        assertThat(updatedAuthor).isEqualTo(expected)

        val gotAuthor = authorRepo.findByIdOrNull(authorId)
        assertThat(gotAuthor).isNotNull()
        assertThat(gotAuthor).isEqualTo(expected)
    }
}