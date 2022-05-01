package ru.mail.polis

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.propose.ProposeED
import ru.mail.polis.dao.propose.ProposeStatus
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

class TestData {
    companion object {
        val testOwnerEmail = "testOwnerPropose@mail.ru"
        val testRenterEmail = "testRenterPropose@mail.ru"

        fun getUser(): UserED {
            return UserED(
                email = "testUserEmail@mail.ru",
                name = "Ilya",
                surname = "Tester",
                phone = "8800553535",
                age = 21,
            )
        }

        fun getUpdatedUser(): UserED {
            val user = getUser()

            user.name = "Dima"
            user.surname = "Sosister"
            user.age = 22


            return user
        }

        fun getBitMap(appContext: Context): Bitmap = BitmapFactory.decodeResource(
            appContext.getResources(),
            R.drawable.picture
        );

        fun createTestApartment(): ApartmentED {
            return ApartmentED.Builder.createBuilder()
                .email("test@test.test")
                .metro(Metro.DEVYATKINO)
                .roomCount(RoomCount.FIVE_MORE)
                .apartmentSquare(200L)
                .apartmentCosts(300)
                .photosUrls(listOf("test"))
                .build()
        }

        fun createUpdatedApartment(oldApartment: ApartmentED): ApartmentED {
            return ApartmentED.Builder.createBuilder()
                .email(oldApartment.email!!)
                .metro(oldApartment.metro!!)
                .roomCount(oldApartment.roomCount!!)
                .apartmentSquare(oldApartment.apartmentSquare!!)
                .apartmentCosts(oldApartment.apartmentCosts!!)
                .photosUrls(oldApartment.photosUrls)
                .build()
        }

        fun createTestPerson(): PersonED {
            return PersonED.Builder.createBuilder()
                .email("test@test.test")
                .tags(listOf())
                .metro(Metro.PARNASSUS)
                .money(0L, 0L)
                .rooms(listOf(RoomCount.ONE))
                .description("test")
                .build()
        }

        fun createUpdatedPerson(oldPerson: PersonED): PersonED {
            return PersonED.Builder.createBuilder()
                .email(oldPerson.email!!)
                .tags(oldPerson.tags!!)
                .metro(oldPerson.metro!!)
                .money(oldPerson.moneyFrom, oldPerson.moneyTo)
                .rooms(oldPerson.rooms!!)
                .description("")
                .build()
        }

        fun getPropose(): ProposeED {
            return ProposeED(
                ownerEmail = testOwnerEmail,
                renterEmail = testRenterEmail,
                status = ProposeStatus.PENDING
            )
        }

        fun getUpdatedPropose(): ProposeED {
            val updated = getPropose()
            updated.status = ProposeStatus.ACCEPTED
            return updated
        }

        fun getTenProposesWithOneOwner(): List<ProposeED> {
            val list = ArrayList<ProposeED>()

            for (i in 1..10) {
                list.add(
                    ProposeED(
                        ownerEmail = testOwnerEmail,
                        renterEmail = testRenterEmail + 1,
                        status = ProposeStatus.PENDING
                    )
                )
            }

            return list
        }

        fun getTenProposesWithOneRenter(): List<ProposeED> {
            val list = ArrayList<ProposeED>()

            for (i in 1..10) {
                list.add(
                    ProposeED(
                        ownerEmail = testOwnerEmail + i,
                        renterEmail = testRenterEmail,
                        status = ProposeStatus.PENDING
                    )
                )
            }

            return list
        }

        fun getTwoUsers(): List<UserED> {
            val list = ArrayList<UserED>()

            for (i in 1..2) {
                list.add(
                    UserED(
                        email = "testUserEmail@mail.ru" + i,
                        name = "Ilya" + i,
                        surname = "Tester" + i,
                        phone = "8800553535",
                        age = 21,
                    )
                )
            }

            return list
        }

        fun getTwoUserList(): List<UserED> {
            val userList = ArrayList<UserED>()
            for (i in 1..2) {
                userList.add(
                    UserED(
                        email = "testUserEmail@mail.ru" + i,
                        name = "Ilya" + i,
                        surname = "Tester" + i,
                        phone = "8800553535",
                        age = 21,
                    )
                )
            }

            return userList
        }


        fun getTwoPersonList(): List<PersonED> {
            val personList = ArrayList<PersonED>()

            for (i in 1..2) {
                personList.add(
                    PersonED.Builder.createBuilder()
                        .email("test@test.test$i")
                        .tags(listOf())
                        .metro(Metro.PARNASSUS)
                        .money(0L, 0L)
                        .rooms(listOf(RoomCount.ONE))
                        .description("test")
                        .build()
                )
            }
            return personList
        }

        fun getTenUsersList(): List<UserED> {
            val userList = ArrayList<UserED>()
            for (i in 1..10) {
                userList.add(
                    UserED(
                        email = "testUserEmail@mail.ru" + i,
                        name = "Ilya" + i,
                        surname = "Tester" + i,
                        phone = "8800553535",
                        age = 21,
                    )
                )
            }

            return userList
        }
    }
}