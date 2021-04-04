package ru.mail.polis

import android.app.ActionBar
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext

class PeopleAdapter(
        private val people: List<Person>
) : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.people_item, parent, false)
        return PeopleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(people[position])
    }

    override fun getItemCount(): Int {
        return people.size
    }


    class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivPhoto: ImageView = itemView.findViewById(R.id.people_item_iv_photo)
        private val tvName: TextView = itemView.findViewById(R.id.people_item_tv_name)
        private val tvAge: TextView = itemView.findViewById(R.id.people_item_tv_age)
        private val llRooms: LinearLayout = itemView.findViewById(R.id.people_item_ll_rooms)
        private val llIvTags: List<ImageView> = listOf(
                itemView.findViewById(R.id.people_item_ll_iv_tag1),
                itemView.findViewById(R.id.people_item_ll_iv_tag2),
                itemView.findViewById(R.id.people_item_ll_iv_tag3),
                itemView.findViewById(R.id.people_item_ll_iv_tag4),
                itemView.findViewById(R.id.people_item_ll_iv_tag5),
                itemView.findViewById(R.id.people_item_ll_iv_tag6),
                itemView.findViewById(R.id.people_item_ll_iv_tag7)
        )
        private val ivMetro: ImageView = itemView.findViewById(R.id.people_item_iv_metro)
        private val tvMetro: TextView = itemView.findViewById(R.id.people_item_tv_metro)
        private val ivBranchColor: ImageView = itemView.findViewById(R.id.people_item_iv_branch_color)
        private val ivMoney: ImageView = itemView.findViewById(R.id.people_item_iv_money)
        private val tvMoney: TextView = itemView.findViewById(R.id.people_item_tv_money)
        //rooms
        private val tvDescription: TextView = itemView.findViewById(R.id.people_item_tv_description)

        fun bind(person: Person) {
            ivPhoto.setImageResource(person.photo)
            tvName.text = person.name
            tvAge.text = person.age
            for (i in 0..6.coerceAtMost(person.tags.size - 1)) {
                llIvTags[i].setImageResource(person.tags[i])
            }
            ivMetro.setImageResource(R.drawable.ic_train_svg_image)
            tvMetro.text = person.metro
            //ivBranchColor.setImageResource(person.branchColor)
            ivMoney.setImageResource(R.drawable.ic_wallet_svg_image)
            tvMoney.text = "от " + person.money.first + " до " + person.money.second
            //llRooms
            tvDescription.text = person.description
        }

    }

}