package ui.data

import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.metro.Metro
import ru.mail.polis.room.RoomCount

data class User(
    val name: String,
    val surname: String,
    val phone: String,
    val age: Int
)

fun getTestUser(): UserED = UserED(
    email = "filmnotification@gmail.com",
    name = "Тест",
    surname = "Тестов",
    phone = "89023334422",
    age = 25
)

fun getUpdatedTestUser(): UserED = UserED(
    email = "filmnotification@gmail.com",
    name = "Петр",
    surname = "Петров",
    phone = "89023334422",
    age = 24
)

fun getTestApartment(): ApartmentED = ApartmentED(
    email = "filmnotification@gmail.com",
    phone = "89023334422",
    metro = Metro.ADMIRALTEYSKAYA,
    roomCount = RoomCount.TWO,
    apartmentSquare = 24,
    apartmentCosts = 15000
)

//TODO сраный чип груп не могу скинуть
fun getUpdatedTestApartment() : ApartmentED = ApartmentED(
    email = "filmnotification@gmail.com",
    phone = "89023334422",
    metro = Metro.DOSTOEVSKAYA,
    roomCount = RoomCount.TWO,
    apartmentSquare = 28,
    apartmentCosts = 17000
)

fun getTestPerson(): PersonED = PersonED(
    email = "filmnotification@gmail.com",
    metro = Metro.ADMIRALTEYSKAYA,
    moneyFrom = 5000,
    moneyTo = 20000,
    metresFrom = 10,
    metresTo = 20,
    rooms = listOf(RoomCount.TWO, RoomCount.THREE),
    description = "I find apartment with two or three rooms, but dont hav money"
)

fun getUpdatedTestPerson(): PersonED = PersonED(
    email = "filmnotification@gmail.com",
    metro = Metro.ACADEMIC,
    moneyFrom = 4000,
    moneyTo = 19000,
    metresFrom = 8,
    metresTo = 17,
    rooms = listOf(RoomCount.TWO, RoomCount.THREE),
    description = "I find apartment with two or three rooms, but dont hav money"
)

fun getPersonToOfferApartment() : PersonED = PersonED(
    email = "lol@gmail.com",
    metro = Metro.ACADEMIC,
    moneyFrom = 4000,
    moneyTo = 19000,
    metresFrom = 8,
    metresTo = 17,
    rooms = listOf(RoomCount.TWO, RoomCount.THREE),
    description = "I find apartment with two or three rooms, but dont hav money"
)

fun getUserToOfferApartment(): UserED = UserED(
    email = "lol@gmail.com",
    name = "Ему",
    surname = "Предалагать",
    phone = "89023334422",
    age = 25
)