package ru.mail.polis.list.of.apartments

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
import com.google.android.material.chip.Chip
import ru.mail.polis.R
import ru.mail.polis.list.ListItemClickListener

class ApartmentsAdapter(
    private var apartmentViews: List<ApartmentView> = emptyList(),
    private val onClickItemListener: ListItemClickListener
) : RecyclerView.Adapter<ApartmentsAdapter.PeopleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_proposed_apartment_item, parent, false)
        return PeopleViewHolder(view)
    }

    override fun getItemCount(): Int = apartmentViews.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(apartmentViews[position])
    }

    fun setData(apartmentViewModels: List<ApartmentView>) {
        this.apartmentViews = apartmentViewModels
        notifyDataSetChanged()
    }

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userAvatar: ImageView =
            itemView.findViewById(R.id.component_person_header__avatar)
        private val apartmentOwnerName: TextView =
            itemView.findViewById(R.id.component_person_header__name)
        private val apartmentOwnerAge: TextView =
            itemView.findViewById(R.id.component_person_header__age)
        private val metroText: TextView =
            itemView.findViewById(R.id.component_proposed_apartment_item__metro_text)
        private val roomCount: Chip =
            itemView.findViewById(R.id.component_proposed_apartment_item__room_count_chip)
        private val metroBranchColor: ImageView =
            itemView.findViewById(R.id.component_proposed_apartment_item__metro_branch_color)
        private val apartmentSquare: TextView =
            itemView.findViewById(R.id.component_proposed_apartment_item__square_text)
        private val apartmentCost: TextView =
            itemView.findViewById(R.id.component_proposed_apartment_item__cost_text)
        private val photoContainer: LinearLayout =
            itemView.findViewById(R.id.component_proposed_apartment_item__photos_container)
        private val cardView: CardView =
            itemView.findViewById(R.id.component_proposed_apartment_item__card_view)

        fun bind(apartments: ApartmentView) {
            if (apartments.ownerAvatar != null) {
                Glide.with(itemView).load(apartments.ownerAvatar).into(userAvatar)
            } else {
                userAvatar.setImageResource(R.drawable.stub_person_avatar)
            }

            metroBranchColor.background.setTint(
                ContextCompat.getColor(
                    itemView.context,
                    apartments.metro.branchColor
                )
            )

            apartmentOwnerName.text = apartments.ownerName
            apartmentOwnerAge.text = "${apartments.ownerAge} лет"
            metroText.text = apartments.metro.stationName
            roomCount.text = apartments.roomCount.label
            apartmentSquare.text = "${apartments.apartmentSquare} м. кв."
            apartmentCost.text = "${apartments.apartmentCosts} Р"

            val photos: List<ImageView> = apartments.photosUrls.map { url ->
                urlToImageView(itemView.context, url)
            }

            photos.forEach(photoContainer::addView)

            cardView.setOnClickListener {
                val clickedPosition = adapterPosition
                onClickItemListener.onListItemClick(clickedPosition)
            }
        }

        private fun urlToImageView(context: Context, url: String): ImageView {
            val iv = ImageView(context)

            iv.layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT
            )

            iv.adjustViewBounds = true
            iv.setPadding(5, 0, 5, 0)

            return Glide.with(itemView).load(url).into(iv).view
        }
    }
}
