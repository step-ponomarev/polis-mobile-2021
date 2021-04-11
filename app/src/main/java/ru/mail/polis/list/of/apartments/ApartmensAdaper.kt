package ru.mail.polis.list.of.apartments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.mail.polis.R

class ApartmensAdaper:  {


    class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userAvatar: View = itemView.findViewById(R.id.person_header__avatar)

    }
}