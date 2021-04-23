package ru.mail.polis.dao

import ru.mail.polis.list.of.people.Person

object PersonService: IPersonService {
    override fun findByEmail(email: String): PersonED {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<PersonED> {
        TODO("Not yet implemented")
    }

    override fun addPerson(person: Person): PersonED {
        TODO("Not yet implemented")
    }
}