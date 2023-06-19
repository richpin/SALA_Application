package com.sala.iotlab.sala_app.activity

import android.app.Activity
import android.os.Bundle
import android.view.Window
import at.lukle.clickableareasimage.ClickableAreasImage
import at.lukle.clickableareasimage.OnClickableAreaClickedListener
import com.sala.iotlab.sala_app.Buildings.SungKyunKwanUniversitySuwonCampus
import com.sala.iotlab.sala_app.R
import com.sala.iotlab.sala_app.data.RoomData
import kotlinx.android.synthetic.main.activity_building_floor.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import uk.co.senab.photoview.PhotoViewAttacher

class BuildingFloorActivity : Activity(), OnClickableAreaClickedListener<RoomData> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_building_floor)

        val intent = intent
        val whichBuilding = intent.getStringExtra("buildingNum")
        val whichFloor = intent.getIntExtra("floor", 0)

        val buildingData = SungKyunKwanUniversitySuwonCampus.BuildingCollection[whichBuilding!!]!!
        floorMap.setImageResource(buildingData?.getBuildingFloorMap()!![whichFloor - 1])

        val clickableAreasImage = ClickableAreasImage(PhotoViewAttacher(floorMap), this)
        clickableAreasImage.clickableAreas = buildingData?.getBuildingFloorClickable()!![whichFloor - 1]
    }

    override fun onClickableAreaTouched(item: RoomData) {
        startActivity<IoTSelectionActivity>(
            "buildingMap" to item.getMap(),
            "roomNum" to item.getNum(),
            "buildingInfo" to item.getBuilding()
        )
    }
}
