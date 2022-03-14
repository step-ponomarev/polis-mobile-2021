import org.junit.Assert
import org.junit.Test
import ru.mail.polis.helpers.getAgeStringWithoutContext

class AgeTest {
    @Test
    fun godaTest() {
        val ageStringWithoutContext = getAgeStringWithoutContext(24)

        Assert.assertTrue(ageStringWithoutContext.compareTo("24 года") == 0)
    }

    @Test
    fun letTest() {
        val ageStringWithoutContext = getAgeStringWithoutContext(20)

        Assert.assertTrue(ageStringWithoutContext.compareTo("20 лет") == 0)
    }

    @Test
    fun godTest() {
        val ageStringWithoutContext = getAgeStringWithoutContext(21)

        Assert.assertTrue(ageStringWithoutContext.compareTo("21 год") == 0)
    }
}