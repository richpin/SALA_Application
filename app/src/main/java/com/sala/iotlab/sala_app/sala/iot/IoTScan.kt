package com.sala.iotlab.sala_app.sala.iot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import com.sala.iotlab.sala_app.data.ScanData
import com.sala.iotlab.sala_app.data.ScanDataManager
import com.sala.iotlab.sala_app.sala.device.DevicePositionManager
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

/**
 * IoT 기기들을 스캔하여 SALA 서버로 전송
 * @author 유영석
 */
class IoTScan(
    val mContext: Context,
    val wifiManager: WifiManager,
    internal val devicePos: DevicePositionManager,
    val ip: String = "115.145.178.188"
) : AnkoLogger {
    companion object {
        private const val SEND_DATA_PERIOD: Int = 10 // 데이터 수집 횟수
        private const val PORT_NUMBER: Int = 50007
        private const val SLEEP_MILLISECOND: Long = 1000 // WiFi 스캔 간격(ms)
    }


    private var mReceiver: WifiReceiver

    var scanDatum: HashMap<String, ScanData>


    init {
        mReceiver = WifiReceiver()
        scanDatum = hashMapOf()

        mContext.registerReceiver(mReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
    }


    /**
     * WiFiManager 스캔 시작
     */
    fun startScan() {
        val scanThread = ScanThread()
        scanThread.start()
    }


    /**
     * [SLEEP_MILLISECOND] 간격으로 [WifiManager]를 이용해 [WifiManager.startScan]을 수행하고
     * [SEND_DATA_PERIOD] 회 만큼 스캔을 하여 [getScanResult] 로 저장하면
     * 스캔한 결과들인 [scanDatum]을 SALA 서버로 보냄
     */
    private inner class ScanThread : Thread() {
        override fun run() {
            while (true) {
                try {
                    for (count in 0..SEND_DATA_PERIOD) {
                        wifiManager.startScan() // 추후 API 업데이트되면 갱신 필요
                        debug("스캔이 시작됨")
                        sleep(SLEEP_MILLISECOND)
                    }
                    with(DataOutputStream(Socket(ip, PORT_NUMBER).getOutputStream())) {
                        synchronized(scanDatum) {
                            scanDatum.values.filter { it.scans.isNotEmpty() }.forEach {
                                write(ScanDataManager.toString(it).toByteArray(Charsets.UTF_8))
                                it.scans.clear()
                                debug("SALA 서버에 내용을 보냄")
                            }
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 스캔한 결과인 [WifiManager.getScanResults] 값들을
     * [ScanData] [HashMap]인 [scanDatum]에 저장하도록 [BroadcastReceiver] 구현
     */
    private inner class WifiReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val scanList: List<ScanResult> = wifiManager.scanResults

            scanList.filter { it.SSID.isNotEmpty() }.forEach {
                synchronized(scanDatum) {
                    if (!scanDatum.containsKey(it.BSSID)) {
                        scanDatum[it.BSSID] = ScanData(it.SSID, it.BSSID)
                    }
                    ScanDataManager.addSingleData(scanDatum[it.BSSID]!!, devicePos.xPos, devicePos.yPos, it)
                    debug { "스캔 리스트 -> BSSID : ${it.BSSID} , SSID : ${it.SSID} , x좌표 : ${devicePos.xPos} , y좌표 : ${devicePos.yPos} . " +
                            "RSSI : ${it.level} , TimeStamp : ${it.timestamp}" }
                }
            }
            debug { "onReceive : 스캔이 끝나고 결과값을 가져옴" }
        }
    }
}
