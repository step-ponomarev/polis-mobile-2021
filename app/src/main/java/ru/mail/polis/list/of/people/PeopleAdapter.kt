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
import ru.mail.polis.helpers.getAgeString
import ru.mail.polis.list.ListItemClickListener
import ru.mail.polis.tags.Tags

class PeopleAdapter(
    private var personViews: List<PersonView>,
    private val mOnClickListener: ListItemClickListener
) : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.people_item, parent, false)
        return PeopleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(personViews[position])
    }

    fun setData(listOfPersonViews: List<PersonView>) {
        this.personViews = listOfPersonViews
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return personViews.size
    }

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var cardView: CardView = itemView.findViewById(R.id.people_item__card_view)
        private val ivPhoto: ImageView = itemView.findViewById(R.id.component_person_header__avatar)
        private val tvName: TextView = itemView.findViewById(R.id.component_person_header__name)
        private val tvAge: TextView = itemView.findViewById(R.id.component_person_header__age)
        private val llIvTags: LinearLayout = itemView.findViewById(R.id.people_item__ll_tags)
        private val tvMetro: TextView = itemView.findViewById(R.id.people_item__metro_text)
        private val ivBranchColor: ImageView =
            itemView.findViewById(R.id.people_item__metro_branch_color)
        private val tvMoney: TextView = itemView.findViewById(R.id.people_item__tv_money)
        private val tagBottomLineDivider: View =
            itemView.findViewById(R.id.people_item__tag_line_divider)

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
        private val tvDescription: TextView =
            itemView.findViewById(R.id.people_item__tv_description)

        fun bind(personView: PersonView) {
            if (personView.photo != null) {
                urlToMyImageView(ivPhoto, personView.photo!!)
            }

            tvName.text = personView.name
            tvAge.text = personView.age?.let { getAgeString(it) }
            val tags: List<ImageView> = personView.tags.map { tag ->
                tagToImageView(itemView.context, tag)
            }

            if (tags.isEmpty()) {
                tagBottomLineDivider.visibility = View.GONE
            } else {
                tags.forEach(llIvTags::addView)
            }

            if (personView.metro != null) {
                tvMetro.text = personView.metro!!.stationName
                ivBranchColor.background.setTint(
                    ContextCompat.getColor(
                        itemView.context,
                        personView.metro!!.branchColor
                    )
                )
            }
            if (personView.moneyFrom == 0L && personView.moneyTo == 0L) {
                tvMoney.setText(R.string.money_default_value)
            } else {
                tvMoney.text =
                    itemView.context.getString(
                        R.string.money,
                        personView.moneyFrom,
                        personView.moneyTo
                    )
            }

            for (i in 0..3.coerceAtMost(personView.rooms.size - 1)) {
                cvRooms[i].visibility = View.VISIBLE
                tvRooms[i].text = itemView.context.getString(personView.rooms[i].stringId)
            }
            cardView.setOnClickListener {
                val clickedPosition = adapterPosition
                mOnClickListener.onListItemClick(clickedPosition)
            }
            tvDescription.text = personView.description
        }
    }

    private fun tagToImageView(context: Context, tag: Tags): ImageView {
        val iv = ImageView(context)

        val marginLayoutParams = ViewGroup.MarginLayoutParams(45, 45)
        marginLayoutParams.setMargins(0, 0, 20, 0)

        iv.layoutParams = marginLayoutParams
        iv.adjustViewBounds = true
        iv.setImageResource(tag.activeImage)
        return iv
    }

    private fun urlToMyImageView(iv: ImageView, url: String) {
        Glide.with(iv).load(url).into(iv)
    }
}
