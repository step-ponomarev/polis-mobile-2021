package ui

import androidx.test.espresso.action.ViewActions.click
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test

class FirstTest: TestCase() {
    @Test
    fun shouldPassOnNoInternetScanTest() =

        base
        beforeTest {
            activityTestRule.launchActivity(null)
            // some things with the state
        }.afterTest {
            // some things with the state
        }.run {
            step("Open Simple Screen") {
                MainScreen {
                    nextButton {
                        isVisible()
                        click()
                    }
                }
            }

            step("Click button_1 and check button_2") {
                SimpleScreen {
                    button1 {
                        click()
                    }
                    button2 {
                        isVisible()
                    }
                }

}