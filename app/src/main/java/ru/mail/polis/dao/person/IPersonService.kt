package ru.mail.polis.dao.person

import ru.mail.polis.dao.DaoException
import kotlin.jvm.Throws

interface IPersonService {
    @Throws(DaoException::class)
    suspend fun findByEmail(email: String): PersonED?

    @Throws(DaoException::class)
    suspend fun findAll(): List<PersonED>

    @Throws(DaoException::class)
    suspend fun addPerson(person: PersonED): PersonED

    @Throws(DaoException::class)
    suspend fun updatePerson(person: PersonED): PersonED

    @Throws(DaoException::class)
    suspend fun deletePersonByEmail(email: String)
}
