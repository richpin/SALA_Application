package com.sala.iotlab.sala_app.data

class RoomData {
    private val num: Int
    private val map: Int
    private val building: String

    constructor(building_in: String, num_in: Int, map_in: Int) {
        this.num = num_in
        this.map = map_in
        this.building = building_in
    }

    fun getNum(): Int {
        return num
    }

    fun getMap(): Int {
        return map
    }

    fun getBuilding(): String {
        return building
    }
}