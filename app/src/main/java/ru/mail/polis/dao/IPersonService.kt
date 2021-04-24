package ru.mail.polis.dao

interface IPersonService {
    fun findByEmail(email: String): PersonED?
    fun findAll(): List<PersonED>
    fun addPerson(person: PersonED): PersonED
    fun updatePerson(person: PersonED): PersonED
    fun deletePersonByEmail(email: String)
}