package ru.mail.polis.list.of.people

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.mail.polis.R

class PeopleAdapter(
    private val people: List<Person>,
    private val listener: ListItemClickListener
) : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {


    private val mOnClickListener: ListItemClickListener = listener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.people_item, parent, false)
        return PeopleViewHolder(view)
    }
    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(people[position])
    }

    interface ListItemClickListener {
        fun onListItemClick(clickedItemIndex: Int)
    }
    override fun getItemCount(): Int {
        return people.size
    }


    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var cardView: CardView = itemView.findViewById(R.id.people_item__card_view)
        private val ivPhoto: ImageView = itemView.findViewById(R.id.component_person_header__avatar)
        private val tvName: TextView = itemView.findViewById(R.id.component_person_header__name)
        private val tvAge: TextView = itemView.findViewById(R.id.component_person_header__age)
        private val llIvTags: LinearLayout = itemView.findViewById(R.id.people_item__ll_tags)
        private val tvMetro: TextView = itemView.findViewById(R.id.people_item__metro_text)
        private val ivBranchColor: ImageView = itemView.findViewById(R.id.people_item__metro_branch_color)
        private val tvMoney: TextView = itemView.findViewById(R.id.people_item__tv_money)


        private val cvRooms: List<CardView> = listOf(
            itemView.findViewById(R.id.people_item__ll_cv_rooms1),
            itemView.findViewById(R.id.people_item__ll_cv_rooms2),
            itemView.findViewById(R.id.people_item__ll_cv_rooms3)
        )
        private val tvRooms: List<TextView> = listOf(
            itemView.findViewById(R.id.people_item__ll_tv_rooms1),
            itemView.findViewById(R.id.people_item__ll_tv_rooms2),
            itemView.findViewById(R.id.people_item__ll_tv_rooms3)
        )
        private val tvDescription: TextView = itemView.findViewById(R.id.people_item__tv_description)

        fun getCardView():CardView = cardView

        fun bind(person: Person) {
            if (person.photo != null) {
                urlToMyImageView(ivPhoto, person.photo)
            }

            tvName.text = person.name
            tvAge.text = person.age
            val tags: List<ImageView> = person.tags.map { url ->
                urlToImageView(itemView.context, url)
            }
            tags.forEach(llIvTags::addView)

            tvMetro.text = person.metro.stationName
            ivBranchColor.background.setTint(
                ContextCompat.getColor(
                    itemView.context,
                    person.metro.branchColor
                )
            )
            tvMoney.text = "от " + person.money.first + " до " + person.money.second
            for (i in 0..3.coerceAtMost(person.rooms.size - 1)) {
                cvRooms[i].visibility = View.VISIBLE
                tvRooms[i].text = person.rooms[i]
            }
            tvDescription.text = person.description
            cardView.setOnClickListener(View.OnClickListener {
                val clickedPosition = adapterPosition
                mOnClickListener.onListItemClick(clickedPosition)
            })
        }

        private fun urlToImageView(context: Context, url: Int): ImageView {
            val iv = ImageView(context)

            iv.layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
            )
            iv.adjustViewBounds = true
            iv.setPadding(5, 5, 10, 5)
            Glide.with(iv).load(url).into(iv)

            return iv
        }
        private fun urlToMyImageView(iv: ImageView, url: String) {
            Glide.with(iv).load(url).into(iv)
        }
    }
}
