package ru.mail.polis.dao

import ru.mail.polis.list.of.people.Person

interface IPersonService {
    fun findByEmail(email: String): PersonED
    fun findAll(): List<PersonED>
    fun addPerson(person: Person): PersonED
    fun updatePerson(person: Person)
}