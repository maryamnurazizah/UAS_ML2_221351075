package com.example.celestica.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.celestica.R

class CardAdapter(private val list: List<StarType>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeText: TextView = itemView.findViewById(R.id.typeText)
        val descText: TextView = itemView.findViewById(R.id.descText)
        val starImage: ImageView = itemView.findViewById(R.id.starImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val star = list[position]
        holder.typeText.text = star.type
        holder.descText.text = star.description
        holder.starImage.setImageResource(star.imageRes)
    }

    override fun getItemCount(): Int = list.size
}
