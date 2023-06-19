package com.sala.iotlab.sala_app.data

import at.lukle.clickableareasimage.ClickableArea

/**
 * 건물정보를 가진 클래스
 */
class BuildingData {
    private val buildingDepartment: Int
    private val buildingFloor: Int
    private val buildingNum: String
    private val buildingName: String
    private val floorMap: Array<Int>?
    private val floorClickalbe: Array<List<ClickableArea<RoomData>>?>?

    constructor(
        department: Int,
        num: String,
        name: String,
        floor: Int,
        map: Array<Int>?,
        clickable: Array<List<ClickableArea<RoomData>>?>?
    ) {
        this.buildingDepartment = department
        this.buildingFloor = floor
        this.buildingNum = num
        this.buildingName = name
        this.floorMap = map
        this.floorClickalbe = clickable
    }

    fun getBuildingDepartment(): Int {
        return this.buildingDepartment
    }

    fun getBuildingFloor(): Int {
        return this.buildingFloor
    }

    fun getBuildingNum(): String {
        return this.buildingNum
    }

    fun getBuildingName(): String {
        return this.buildingName
    }

    fun getBuildingFloorMap(): Array<Int>? {
        return this.floorMap
    }

    fun getBuildingFloorClickable(): Array<List<ClickableArea<RoomData>>?>? {
        return this.floorClickalbe
    }
}