package com.sala.iotlab.sala_app.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import com.sala.iotlab.sala_app.Buildings.SungKyunKwanUniversitySuwonCampus
import com.sala.iotlab.sala_app.R
import com.sala.iotlab.sala_app.sala.helper.BuildingViewAdapter
import com.sala.iotlab.sala_app.sala.helper.FloorViewAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val photoView = findViewById(R.id.campusmap) as PhotoView
        photoView.setImageResource(R.drawable.campusmap)

        for (i in 0..(SungKyunKwanUniversitySuwonCampus.BuildingInfo.size - 1)) {
            findViewById<RecyclerView>(SungKyunKwanUniversitySuwonCampus.BuildingRecycelrs[i]).apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = BuildingViewAdapter(SungKyunKwanUniversitySuwonCampus.BuildingInfo[i])
            }
        }

        findViewById<RecyclerView>(R.id.floor).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = FloorViewAdapter(0)
        }
    }
}

