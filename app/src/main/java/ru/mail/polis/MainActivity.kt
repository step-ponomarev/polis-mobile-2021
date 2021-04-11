package ru.mail.polis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.mail.polis.ui.fragments.ListOfProposedApartmentsFragment
import ru.mail.polis.ui.fragments.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            replace<ListOfProposedApartmentsFragment>(R.id.fragment_container_view)
            setReorderingAllowed(true)
            addToBackStack(LoginFragment.NAME)
        }
    }
}
