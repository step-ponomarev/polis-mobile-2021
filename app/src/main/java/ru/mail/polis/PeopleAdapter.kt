package ru.mail.polis

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeopleAdapter(
        private val people :List<Person>
) :RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {


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


    class PeopleViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        private val ivPhoto : ImageView = itemView.findViewById(R.id.people_item_iv_photo)
        private val tvName : TextView = itemView.findViewById(R.id.people_item_tv_name)
        private val tvAge : TextView = itemView.findViewById(R.id.people_item_tv_age)
        private val llTags : LinearLayout = itemView.findViewById(R.id.people_item_ll_tags)
        private val ivMetro : ImageView = itemView.findViewById(R.id.people_item_iv_metro)
        private val tvMetro : TextView = itemView.findViewById(R.id.people_item_tv_metro)
        private val ivBranchColor : ImageView = itemView.findViewById(R.id.people_item_iv_branch_color)
        private val ivMoney : ImageView = itemView.findViewById(R.id.people_item_iv_money)
        private val tvMoney : TextView = itemView.findViewById(R.id.people_item_tv_money)
        private val llRooms : LinearLayout = itemView.findViewById(R.id.people_item_ll_rooms)
        private val tvDescription : TextView = itemView.findViewById(R.id.people_item_tv_description)

        fun bind(person: Person) {
            ivPhoto.setImageResource(person.photo)
            tvName.text = person.name
            tvAge.text = person.age
            //llTags
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