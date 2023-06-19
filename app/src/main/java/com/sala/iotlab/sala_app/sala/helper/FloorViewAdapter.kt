package com.sala.iotlab.sala_app.sala.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sala.iotlab.sala_app.R
import com.sala.iotlab.sala_app.activity.BuildingFloorActivity
import com.sala.iotlab.sala_app.activity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.floor_item.view.*
import org.jetbrains.anko.startActivity


class FloorViewAdapter(private val floor: Int) :
    RecyclerView.Adapter<FloorViewAdapter.FloorViewHolder>() {
    private val floorList = (1..floor).toList().toTypedArray()

    inner class FloorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val floorNum: TextView

        init {
            floorNum = itemView.findViewById(R.id.floor_text)
            itemView.setOnClickListener {
                (itemView.context as MainActivity).startActivity<BuildingFloorActivity>(
                    "buildingNum" to (itemView.context as MainActivity).whichBuildingNum.text.toString(),
                    "floor" to floorList[adapterPosition]
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.floor_item, parent, false)
        return FloorViewHolder(view)
    }

    override fun onBindViewHolder(holder: FloorViewHolder, position: Int) {
        holder.floorNum.text = floorList[position].toString() + "F"
    }

    override fun getItemCount(): Int {
        return floorList.size
    }
}