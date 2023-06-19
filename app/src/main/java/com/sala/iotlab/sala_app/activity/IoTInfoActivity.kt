package com.sala.iotlab.sala_app.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.sala.iotlab.sala_app.R
import com.sala.iotlab.sala_app.data.IoTData
import kotlinx.android.synthetic.main.activity_iotinfo.*
import org.jetbrains.anko.toast

class IoTInfoActivity : Activity() {
    private var isOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_iotinfo)

        val intent = intent
        val iotData = intent.getParcelableExtra<IoTData>("data")

        iotDeviceType.text = iotData.deviceType
        iotDevicePosition.text = "X 좌표 : ${iotData.x}  Y 좌표 : ${iotData.y}"
        iotDeviceDnsName.text = iotData.dnsName
        iotDeviceIpAddress.text = iotData.ipAddress
        light_control.setImageResource(R.drawable.light_on)

        light_control.setOnClickListener {
            isOn = !isOn
            toast("눌림!")

            if(isOn) {
                lightOn()
                toast("램프를 켬")
            }
            else{
                lightOff()
                toast("램프를 끔")
            }
        }
    }

    private fun lightOn(){
        light_control.setImageResource(R.drawable.light_off)
    }

    private fun lightOff(){
        light_control.setImageResource(R.drawable.light_on)
    }
}
