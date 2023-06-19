package com.sala.iotlab.sala_app.sala

import android.content.Context
import android.widget.ImageView
import com.sala.iotlab.sala_app.activity.IoTSelectionActivity
import com.sala.iotlab.sala_app.sala.device.DevicePositionManager
import com.sala.iotlab.sala_app.sala.helper.ButtonMaker
import com.sala.iotlab.sala_app.sala.helper.SizeConverter
import org.jetbrains.anko.AnkoLogger

class SALAManager(
    public val mContext: Context,
    public val targetImageView: ImageView,
    public val ioTSelectionActivity: IoTSelectionActivity? = null,
    public val xMax: Int = 800, public val yMax: Int = 900
) : AnkoLogger {

    public val mDevicePositionManager = DevicePositionManager(this)
    public val mSizeConverter = SizeConverter
    public val mButtonMaker = ButtonMaker(this)

    private var xDrawMax = 0
    private var yDrawMax = 0

    fun calcActualImageViewSize(){
        xDrawMax = targetImageView.measuredWidth
        yDrawMax = targetImageView.measuredHeight
    }

    fun requestForButton(){
        return
    }

    fun positionRegisterAll() {
        mDevicePositionManager.registerAll()
    }

    fun positionUnregisterAll() {
        mDevicePositionManager.unregisterAll()
    }

    fun positionRegisterSpecific(targetCode: Int) {
        mDevicePositionManager.registerSpecific(targetCode)
    }

    fun positionUnregisterSpecific(targetCode: Int) {
        mDevicePositionManager.unregisterSpecific(targetCode)
    }

    fun getDevicePositonManager(): DevicePositionManager{
        return mDevicePositionManager
    }
}