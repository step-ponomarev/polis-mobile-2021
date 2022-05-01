package ui.screens

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.mail.polis.R
import ru.mail.polis.dao.apartments.ApartmentED
import ru.mail.polis.dao.person.PersonED
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.ui.fragments.ListOfPeopleFragment

object ListOfPeopleScreen : KScreen<ListOfPeopleScreen>() {
    override val layoutId: Int? = R.layout.fragment_list_of_people
    override val viewClass: Class<*>? = ListOfPeopleFragment::class.java

    val peopleRecycler = KRecyclerView(
        builder = { withId(R.id.list_of_people__rv_list) },
        itemTypeBuilder = { itemType(::PeopleRecyclerItem) }
    )

    fun navigateToSettings() {
        settingsButton {
            isVisible()
            isClickable()
            click()
        }
    }

    fun isLoaded() {
        peopleRecycler {
            isVisible()
        }
    }

    fun offerApartment(
        apartmentED: ApartmentED,
        personToOfferApartmentED: PersonED,
        userToOffer: UserED
    ) {

        val nameAndSurname = "${userToOffer.name!!} ${userToOffer.surname!!}"
        peopleRecycler {
            childWith<PeopleRecyclerItem> {
                withDescendant {
                    withText(nameAndSurname)
                }
            } perform {
                personName {
                    isDisplayed()
                    hasText(nameAndSurname)
                }
//                personAge {
//                    hasText(userToOffer.age.toString())
//                }

                click()
            }
        }
    }

    class PeopleRecyclerItem(parent: Matcher<View>) :
        KRecyclerItem<PeopleRecyclerItem>(parent) {
        val personName: KTextView = KTextView(parent) { withId(R.id.component_person_header__name) }
        val personAge: KTextView = KTextView(parent) { withId(R.id.component_person_header__age) }
        val personMetro: KTextView = KTextView(parent) { withId(R.id.people_item__metro_text) }
        val personPrice: KTextView = KTextView(parent) { withId(R.id.people_item__tv_money) }
    }

    val settingsButton = KButton { withId(R.id.nav_graph__settings_fragment) }
}