package ru.mail.polis.unit

import junit.framework.TestCase.assertFalse
import org.junit.Test
import ru.mail.polis.room.RoomCount

class RoomCountTest {

    private val roomCount = 1;
    private val studio = "Студия"

    @Test
    fun foundTest() {
        val studioRoomCount = RoomCount.from(studio)

        assert(studioRoomCount == RoomCount.STUDIO)
        assert(roomCount == studioRoomCount.roomCount)
        assert(studio == studioRoomCount.label)
    }

    @Test
    fun notFoundTest() {
        var found = true;
        try {
            val studioRoomCount = RoomCount.from("сто")
        } catch (e: Exception) {
            found = false
            assert(e is IllegalArgumentException)
        }

        assertFalse(found)
    }
}