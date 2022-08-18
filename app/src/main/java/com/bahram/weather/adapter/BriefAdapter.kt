package com.bahram.weather.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather.MainActivity
import com.bahram.weather.MainActivity222
import com.bahram.weather.R

class BriefAdapter(var context: Context, val headerList: ArrayList<MainActivity.DataForRecycleView1?>?) :
    RecyclerView.Adapter<BriefAdapter.BriefViewHolder>() {


//    lateinit var ccc: Context

    class BriefViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llSelected: LinearLayout = itemView.findViewById(R.id.ll_selected)
        val tvCityNameB: TextView = itemView.findViewById(R.id.tv_city_name_b)
        val tvDescriptionB: TextView = itemView.findViewById(R.id.tv_description_b)
        val tvTempB: TextView = itemView.findViewById(R.id.tv_temp_b)
        val tvTempMaxB: TextView = itemView.findViewById(R.id.tv_temp_max_b)
        val tvTempMinB: TextView = itemView.findViewById(R.id.tv_temp_min_b)

        var ccc = itemView.context
//        fun bind(header: MainActivity.DataForRecycleView1) {
//            tvCityNameB.text = header.cityName
//            tvDescriptionB.text = header.description
//            tvTempB.text = header.currentTemp
//            tvTempMaxB.text = header.tempMax
//            tvTempMinB.text = header.tempMin
//        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BriefViewHolder {
        return BriefViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_brief, parent, false))
    }

    override fun onBindViewHolder(holder: BriefViewHolder, position: Int) {
        val item11 = headerList?.get(position)
        holder.tvCityNameB.text = item11?.cityName
        holder.tvDescriptionB.text = item11?.description
        holder.tvTempB.text = item11?.currentTemp
        holder.tvTempMaxB.text = item11?.tempMax
        holder.tvTempMinB.text = item11?.tempMin

        holder.llSelected.setOnClickListener {


            val intent = Intent(context, MainActivity222::class.java)
//            intent.putExtra("int", item11.hashCode())
            intent.putExtra("rec", item11?.cityName)
            holder.llSelected.context.startActivity(intent)


        }

    }

    override fun getItemCount(): Int {
        return headerList?.size ?: 0
    }
}