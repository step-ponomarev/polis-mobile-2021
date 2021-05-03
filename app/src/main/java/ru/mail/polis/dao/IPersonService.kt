package ru.mail.polis.dao

interface IPersonService {
    suspend fun findByEmail(email: String): PersonED?
    suspend fun findAll(): List<PersonED>
    suspend fun addPerson(person: PersonED): PersonED
    suspend fun updatePerson(person: PersonED): PersonED
    suspend fun deletePersonByEmail(email: String)
}
