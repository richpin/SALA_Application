package com.sala.iotlab.sala_app.data

import android.net.wifi.ScanResult
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 * ScanData의 처리에 관한 내용을 다루는 매니저
 * @author 유영석
 */
object ScanDataManager : AnkoLogger{

    /**
     * [ScanData] 객체인 [scanData]의 [ScanData.scans]에
     * [x], [y], [result]를 갖는 [SingleData]를 추가
     */
    fun addSingleData(scanData: ScanData, x: Int, y: Int, result: ScanResult) {
        scanData.scans.add(SingleData(x, y, result.level, result.timestamp))
    }

    /**
     * [ScanData] 객체인 [scanData]를 SALA 서버와 통신하기 위한
     * 문자열로 변환하여 반환
     */
    fun toString(scanData: ScanData): String {
        val sb = StringBuilder()
        with(scanData) {
            sb.append("$iotName\n$macAddress\n$ipAddress\n")
            scans.forEach {
                sb.append("$it.timeStamp $it.x $it.y $it.rssi\n")
            }
        }
        debug { "${sb.toString()}으로 통신용 문자열로 변환" }
        return sb.toString()
    }
}