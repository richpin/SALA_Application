package com.sala.iotlab.sala_app.activity

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.sala.iotlab.sala_app.R
import kotlinx.android.synthetic.main.activity_iotselection.*
import com.google.android.material.button.MaterialButton
import com.sala.iotlab.sala_app.data.IoTData
import com.sala.iotlab.sala_app.data.IoTDataManager
import com.sala.iotlab.sala_app.sala.SALAManager
import com.sala.iotlab.sala_app.sala.connect.NetWorking
import com.sala.iotlab.sala_app.sala.device.DevicePositionManager
import com.sala.iotlab.sala_app.sala.helper.ButtonMaker
import com.sala.iotlab.sala_app.sala.iot.IoTScan
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.io.DataInputStream
import java.io.IOException
import java.io.InputStream
import java.net.ConnectException
import java.net.Socket

class IoTSelectionActivity() : AppCompatActivity(), AnkoLogger {
    private lateinit var salaManager: SALAManager
    private lateinit var iotScan: IoTScan
    private lateinit var netWorkThread: NetWorking.NetworkThread
    private lateinit var netWorkHandler: NetWorking.NetworkResultHandler
    private lateinit var iotDevices: HashMap<String, IoTData>
    private lateinit var iotButtons: HashMap<String, MaterialButton>
    private lateinit var buttonMaker: ButtonMaker

    private lateinit var humanButton: MaterialButton

    companion object {
        public lateinit var mContext: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iotselection)
        mContext = this
        salaManager = SALAManager(this, roomMap, this)
        buttonMaker = ButtonMaker(salaManager)

        val intent = intent
        val map = intent.getIntExtra("buildingMap", 0)
        val room = intent.getIntExtra("roomNum", 0)
        val building = intent.getStringExtra("buildingInfo")

        refresh.setOnClickListener {
            finish()
            startActivity(intent)
        }

        roomMap.setImageResource(map)
        buildingInfo.text = building
        roomNum.text = room.toString() + "호"

        /*
        buttonMaker = ButtonMaker(this)

        netWorkHandler = NetWorking.NetworkResultHandler()
        netWorkThread = NetWorking.NetworkThread(netWorkHandler)

        iotDevices = HashMap()
        iotButtons = HashMap()

        netWorkThread.start()

        salaManager = SALAManager(this, roomMap)
        salaManager.calcActualImageViewSize()

        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        iotScan = IoTScan(this, wifiManager, salaManager.getDevicePositonManager())
        iotScan.startScan()
        */
    }

    private fun generateSomething(){
        Handler().postDelayed({
            var myButton = buttonMaker.generateButton(
                IoTData("CCTV", "NAME1", "1.2.3.4", 0, 50)
            )
            iotselectionLayout.addView(myButton, myButton.layoutParams as ConstraintLayout.LayoutParams)

            myButton = buttonMaker.generateButton(
                IoTData("GASSTOVE", "NAME2", "2.3.4.5", 0, 390)
            )
            iotselectionLayout.addView(myButton, myButton.layoutParams as ConstraintLayout.LayoutParams)

            myButton = buttonMaker.generateButton(
                IoTData("LIGHT", "NAME3", "3.4.5.6", 330, 370)
            )
            iotselectionLayout.addView(myButton, myButton.layoutParams as ConstraintLayout.LayoutParams)

            myButton = buttonMaker.generateButton(
                IoTData("TEMPERATURE", "NAME4", "4.5.6.7", 0, 600)
            )
            iotselectionLayout.addView(myButton, myButton.layoutParams as ConstraintLayout.LayoutParams)

            myButton = buttonMaker.generateButton(
                IoTData("SPEAKER", "NAME5", "5.6.7.8", 420, 400)
            )
            iotselectionLayout.addView(myButton, myButton.layoutParams as ConstraintLayout.LayoutParams)

            myButton = buttonMaker.generateButton(
                IoTData("AIRCONDITIONER", "NAME6", "6.7.8.9", 500, 50)
            )
            iotselectionLayout.addView(myButton, myButton.layoutParams as ConstraintLayout.LayoutParams)

            myButton = buttonMaker.generateButton(
                IoTData("TV", "NAME7", "7.8.9.10", 400, 730)
            )
            iotselectionLayout.addView(myButton, myButton.layoutParams as ConstraintLayout.LayoutParams)

            myButton = buttonMaker.generateButton(
                IoTData("VR", "NAME8", "8.9.10.11", 600, 600)
            )
            iotselectionLayout.addView(myButton, myButton.layoutParams as ConstraintLayout.LayoutParams)

        }, 100)
    }

    public fun refreshHumanButton(tarButton: MaterialButton) {
        if (::humanButton.isInitialized) {
            iotselectionLayout.removeView(humanButton)
            humanButton = tarButton
            iotselectionLayout.addView(humanButton)
        } else {
            humanButton = tarButton
            iotselectionLayout.addView(humanButton)
        }
    }

    fun newIoTData(dns_infrom: String) {
        val lines = dns_infrom.split("\n")

        loop1@ for (i in 0 until lines.size step 2) {
            val dnsName = lines[i].split(" ")[1]
            val ipAddr = lines[i + 1].split(" ")[1]
            debug { "InfoProcessing : $dnsName $ipAddr" }

            when (IoTDataManager.checkDNSName(dnsName)) {
                IoTDataManager.NOT_DNS, IoTDataManager.DNS_BUT_NO_POS -> {
                    break@loop1
                }
                IoTDataManager.DNS_WITH_POS -> {
                    val pureDnsName = IoTDataManager.getPureDNSName(dnsName)

                    if (iotDevices.containsKey(pureDnsName))
                        iotDevices.remove(pureDnsName)
                    iotDevices.put(pureDnsName, IoTDataManager.generateIoTData(dnsName, ipAddr))

                    if (iotButtons.containsKey(pureDnsName))
                        iotButtons.remove(pureDnsName)
                    iotButtons.put(pureDnsName, buttonMaker.generateButton(iotDevices.get(pureDnsName)!!))

                    break@loop1
                }
            }
        }
    }

    public fun runSimulation(v: View) {
        salaManager.getDevicePositonManager().registerSpecific(DevicePositionManager.SIMULATE_POSITION)
    }

    public fun doneSimulation(){
        generateSomething()
        if (::humanButton.isInitialized) {
            iotselectionLayout.removeView(humanButton)
        }
    }

    /*
    override fun onResume() {
        super.onResume()
        salaManager.positionRegisterAll()
    }

    override fun onPause() {
        super.onPause()
        salaManager.positionUnregisterAll()
    }
    */
}
