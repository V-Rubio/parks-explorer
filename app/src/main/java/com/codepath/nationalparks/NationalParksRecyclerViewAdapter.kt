package com.codepath.nationalparks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepath.nationalparks.R.id
import com.bumptech.glide.Glide

/**
 * [RecyclerView.Adapter] that can display a [NationalPark] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class NationalParksRecyclerViewAdapter(
    private val parks: List<NationalPark>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<NationalParksRecyclerViewAdapter.ParkViewHolder>() {

    // Inflate the item layout from XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_national_park, parent, false)
        return ParkViewHolder(view)
    }

    // ViewHolder class holds references to all UI elements inside the list item layout
    inner class ParkViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: NationalPark? = null

        // TODO: Step 4a - Add references for remaining views from XML
        val mParkImage: ImageView = mView.findViewById(R.id.park_image)


        val mParkName: TextView = mView.findViewById(id.park_name) as TextView
        val mParkDescription: TextView = mView.findViewById(id.park_description) as TextView

        override fun toString(): String {
            return mParkName.toString() + " '" + mParkDescription.text + "'"
        }
    }

    override fun onBindViewHolder(holder: ParkViewHolder, position: Int) {
        val park = parks[position]

        holder.mItem = park
        holder.mParkName.text = park.name
        holder.mParkDescription.text = park.description

        // Load image using Glide
        val imageUrl = park.imageUrl
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(holder.mView)
                .load(imageUrl)
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(holder.mParkImage)
        } else {
            Glide.with(holder.mView)
                .load(R.drawable.placeholder) // default image when no URL
                .centerInside()
                .into(holder.mParkImage)
        }


        // Sets up click listener for this park item
        holder.mView.setOnClickListener {
            holder.mItem?.let { park ->
                mListener?.onItemClick(park)
            }
        }
    }

    // Tells the RecyclerView how many items to display
    override fun getItemCount(): Int {
        return parks.size
    }
}
