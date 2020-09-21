package com.egabruskiy.distanceus.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.egabruskiy.distanceus.R
import com.egabruskiy.distanceus.models.Person
import com.egabruskiy.distanceus.utils.Utility
import java.util.*


class PeopleRecyclerAdapter:  RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    val TAG = javaClass.simpleName
    private var persons: List<Person> = ArrayList()


    lateinit var mListener: RecyclerViewListener
    var mainLongitude:Double = 0.0
    var mainLatitude:Double = 0.0
      var mainName:String = "Тебя"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_item, parent, false)
        return PostViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bind(persons[position])
        var person = persons[position]
        holder.distanceView.text = Utility.distance(mainLatitude,person.latitude,mainLongitude,person.longitude)
        holder.distanceTo.text = mainName

    }


    class PostViewHolder(itemView: View,private val listener: RecyclerViewListener) : RecyclerView.ViewHolder(itemView){

        private var personName: TextView = itemView.findViewById(R.id.person_name)
        private var avatarView: ImageView = itemView.findViewById(R.id.user_ava)
        private var itemLayout : LinearLayout = itemView.findViewById(R.id.item_layout)
         var distanceView: TextView = itemView.findViewById(R.id.distance_view)
         var distanceTo: TextView = itemView.findViewById(R.id.distance_to)
        val TAG = javaClass.simpleName

        fun bind(person: Person ) {
            personName.text = person.name


            Glide.with(itemView.context)
                .load(person.avatar)
                .circleCrop()
                .into(avatarView)

            itemLayout.setOnClickListener{

                listener.onClick(item = person,position = adapterPosition)
            }
        }
    }
    override fun getItemCount(): Int {
        return persons.size
    }

    fun setPersons(persons: List<Person>) {
        this.persons = persons
        notifyDataSetChanged()
    }

    fun setClickListener(listener: RecyclerViewListener){
        mListener = listener
    }

}


