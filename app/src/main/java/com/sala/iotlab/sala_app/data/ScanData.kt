package com.sala.iotlab.sala_app.data

/**
 * 특정 Address 를 가진 기기에 대한 스캔정보들 묶음
 */
data class ScanData(
    var iotName: String,
    var macAddress: String,
    var ipAddress: String = "0.0.0.0",
    var scans: ArrayList<SingleData> = ArrayList()
)