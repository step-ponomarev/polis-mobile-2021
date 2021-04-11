package ru.mail.polis.list.of.apartments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ru.mail.polis.R

class ApartmensAdaper(
    private val apartments: List<Apartment>
) : RecyclerView.Adapter<ApartmensAdaper.PeopleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.proposed_apartment_item, parent, false)
        return PeopleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return apartments.size
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(apartments[position])
    }

    class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userAvatar: ImageView = itemView.findViewById(R.id.person_header__avatar)
        private val apartmentOwnerName: TextView = itemView.findViewById(R.id.person_header__name)
        private val apartmentOwnerAge: TextView = itemView.findViewById(R.id.person_header__age)
        private val metroText: TextView =
            itemView.findViewById(R.id.proposed_apartment_item_metro__text)

        // как изменить цвет?
        private val metroBranchColor: ImageView =
            itemView.findViewById(R.id.proposed_apartment_item_metro__branch_color)
        private val apartmentSquare: TextView =
            itemView.findViewById(R.id.proposed_apartment_item_square__text)
        private val apartmentCost: TextView =
            itemView.findViewById(R.id.proposed_apartment_item_cost__text)

        // шота придумать, как добавлять фоточки?
//        private val apartmentPhotoContainer: ConstraintLayout =
//            itemView.findViewById(R.id.proposed_apartment_item_photos)

        fun bind(apartments: Apartment) {
            userAvatar.setImageResource(apartments.ownerAvatar ?: R.drawable.stub_person_avatar)
            apartmentOwnerName.text = apartments.ownerName
            apartmentOwnerAge.text = "${apartments.ownerAge} лет"
            metroText.text = apartments.metro.stationName
            apartmentSquare.text = "${apartments.apartmentSquare} м. кв."
            apartmentCost.text = "${apartments.apartmentCosts} Р"

            // пока не трогаем
//            val photos: List<ImageView> = apartments.apartmentPhotos.map<Int, ImageView> { id ->
//                photoIdToImageView(
//                    itemView.context,
//                    id
//                )
//            }
        }

        private fun photoIdToImageView(context: Context, id: Int): ImageView {
            val iv = ImageView(context)
            iv.setImageResource(id)

            return iv
        }
    }
}
