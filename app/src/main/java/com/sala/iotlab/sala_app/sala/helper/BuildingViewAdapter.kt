package com.sala.iotlab.sala_app.sala.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sala.iotlab.sala_app.Buildings.SungKyunKwanUniversitySuwonCampus
import com.sala.iotlab.sala_app.R
import com.sala.iotlab.sala_app.activity.MainActivity
import com.sala.iotlab.sala_app.data.BuildingData
import kotlinx.android.synthetic.main.activity_main.*


class BuildingViewAdapter(private val buildingList: ArrayList<BuildingData>) :
    RecyclerView.Adapter<BuildingViewAdapter.BuildingViewHolder>() {

    inner class BuildingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buildingNum: TextView
        val buildingName: TextView

        init {
            buildingNum = itemView.findViewById(R.id.buildingNum)
            buildingName = itemView.findViewById(R.id.buildingName)

            itemView.setOnClickListener {
                with(itemView.context as MainActivity) {
                    val which = buildingList[adapterPosition]
                    whichBuildingNum.text = which.getBuildingNum()
                    floor.adapter = FloorViewAdapter(which.getBuildingFloor())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.building_item, parent, false)
        return BuildingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {
        val color = when (buildingList[position].getBuildingDepartment()) {
            1 -> {
                R.color.colorLifeWorkout
            }
            2 -> {
                R.color.colorEngineering
            }
            3 -> {
                R.color.colorNatural
            }
            4 -> {
                R.color.colorResearch
            }
            5 -> {
                R.color.colorDormitory
            }
            6 -> {
                R.color.colorEtc
            }
            else -> {
                R.color.colorPrimary
            }
        }

        with(holder) {
            buildingNum.apply {
                setText(buildingList[position].getBuildingNum())
                setBackgroundResource(color)
            }
            buildingName.apply {
                setText(buildingList[position].getBuildingName())
                setBackgroundResource(color)
            }
        }
    }

    override fun getItemCount(): Int {
        return buildingList.size
    }
}