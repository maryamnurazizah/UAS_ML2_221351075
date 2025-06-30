package com.example.celestica.dataset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.celestica.R

class DatasetAdapter(private val data: List<StarData>) : RecyclerView.Adapter<DatasetAdapter.DatasetViewHolder>() {

    class DatasetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.textItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatasetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dataset_item, parent, false)
        return DatasetViewHolder(view)
    }

    override fun onBindViewHolder(holder: DatasetViewHolder, position: Int) {
        val star = data[position]
        val row = String.format(
            "%-7s | %-9s | %-7s | %-7s | %-4s | %-6s | %-2s",
            star.temperature,
            star.luminosity,
            star.radius,
            star.magnitude,
            star.starType,
            star.starColor,
            star.spectralClass
        )
        holder.text.text = row
    }

    override fun getItemCount(): Int = data.size
}
