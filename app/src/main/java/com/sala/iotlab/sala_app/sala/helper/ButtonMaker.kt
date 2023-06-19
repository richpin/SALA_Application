package com.sala.iotlab.sala_app.sala.helper

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.google.android.material.button.MaterialButton
import com.sala.iotlab.sala_app.R
import com.sala.iotlab.sala_app.activity.IoTInfoActivity
import com.sala.iotlab.sala_app.data.IoTData
import com.sala.iotlab.sala_app.sala.SALAManager
import org.jetbrains.anko.startActivity

class ButtonMaker(val salaManager: SALAManager) {

    fun generateButton(
        ioTData: IoTData,
        width: Int = 50, height: Int = 50,
        xDrawMax: Int = -1, yDrawMax: Int = -1
    ): MaterialButton {

        val myButton = MaterialButton(salaManager.mContext, null, R.style.Widget_MaterialComponents_Button_OutlinedButton)

        with(myButton) {
            setBackgroundResource(R.color.colorTransparent)
            setCompoundDrawablesWithIntrinsicBounds(findImageId(ioTData), 0, 0, 0)
            iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
            iconPadding = 0
            setPadding(0, 0, 0, 0)
            with(
                ConstraintLayout.LayoutParams(
                    (width.toFloat() * salaManager.mContext.resources.displayMetrics.density).toInt(),
                    (height.toFloat() * salaManager.mContext.resources.displayMetrics.density * 1.15f).toInt()
                )
            ) {
                if (salaManager == null) {
                    leftMargin = ioTData.x
                    topMargin = ioTData.y
                } else {
                    leftMargin = SizeConverter.targetConversion(
                        tarDrawMax = salaManager.targetImageView.measuredWidth,
                        tarActual = ioTData.x,
                        tarActualMax = salaManager.xMax,
                        tarDrawOffset = salaManager.targetImageView.x.toInt()
                    )
                    topMargin = SizeConverter.targetConversion(
                        tarDrawMax = salaManager.targetImageView.measuredHeight,
                        tarActual = ioTData.y,
                        tarActualMax = salaManager.yMax,
                        tarDrawOffset = salaManager.targetImageView.y.toInt()
                    )
                }

                topToTop = ConstraintSet.PARENT_ID
                leftToLeft = ConstraintSet.PARENT_ID

                layoutParams = this
            } // 크기 및 위치 설정

            tag = ioTData // 태깅

            setOnClickListener {
                salaManager.mContext.startActivity<IoTInfoActivity>(
                    "data" to ioTData
                )
            } // 터치 시 IoTInfoActivity 켜기 설정

            return this
        }
    }

    private fun findImageId(ioTData: IoTData): Int {
        return when (ioTData.deviceType.toUpperCase()) {
            "TV" -> R.mipmap.tv
            "AIRCONDITIONER" -> R.mipmap.airconditioner
            "LIGHT" -> R.mipmap.light
            "TEMPERATURE" -> R.mipmap.temperature
            "GASSTOVE" -> R.mipmap.gasstove
            "CCTV" -> R.mipmap.cctv
            "SPEAKER" -> R.mipmap.speaker
            "HUMAN" -> R.mipmap.human
            else -> R.mipmap.iot
        }
    }
}