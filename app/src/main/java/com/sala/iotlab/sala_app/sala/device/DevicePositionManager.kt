package com.sala.iotlab.sala_app.sala.device

import android.content.Context
import android.hardware.SensorManager
import com.sala.iotlab.sala_app.R
import com.sala.iotlab.sala_app.data.IoTData
import com.sala.iotlab.sala_app.sala.SALAManager
import com.sala.iotlab.sala_app.sala.helper.ButtonMaker
import com.sala.iotlab.sala_app.sala.test.SimulatePosition
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

/**
 * 각종 위치 결정 클래스들을 바탕으로 디바이스 위치를 최종적으로 결정
 * @author 강성민
 */
class DevicePositionManager(
    val salaManager: SALAManager
) : AnkoLogger {

    companion object {
        const val DEAD_RECKONING: Int = 0x101
        const val SIMULATE_POSITION: Int = 0xfff
    }

    var xPos: Int = 670 // TODO 소프트코딩
    var yPos: Int = 455 // TODO 소프트코딩

    val mSensorManager: SensorManager =
        salaManager.mContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val mDeadReckoning = DeadReckoning(
        this, mSensorManager, salaManager.xMax, salaManager.yMax, xPos, yPos
    )
    private val mSimulatePosition = SimulatePosition(this, SimulatePosition.SIM_SALA_0);

    /**
     * 해당 매니저에서 관리하는 모든 위치측정 기능 켜기
     */
    fun registerAll() {
        mDeadReckoning.registerListener()
        debug { "모든 위치측정 기능을 켬" }
    }

    /**
     * 해당 매니저에서 관리하는 모든 위치측정 기능 끄기
     */
    fun unregisterAll() {
        mDeadReckoning.unregisterListener()
        debug { "모든 위치측정 기능을 끔" }
    }

    /**
     * [targetCode]를 갖는 위치측정 기능 켜기
     */
    fun registerSpecific(targetCode: Int) {
        when (targetCode) {
            DEAD_RECKONING -> mDeadReckoning.unregisterListener()
            SIMULATE_POSITION -> mSimulatePosition.run()
        }
        debug { "${targetCode}를 갖는 위치측정 기능을 켬" }
    }


    /**
     * [targetCode]를 갖는 위치측정 기능 끄기
     */
    fun unregisterSpecific(targetCode: Int) {
        when (targetCode) {
            DEAD_RECKONING -> mDeadReckoning.unregisterListener()
        }
        debug { "${targetCode}를 갖는 위치측정 기능을 끔" }
    }

    /**
     * [fromCode]를 갖는 하위 클래스에서 호출받아서 현재 값 갱신
     */
    fun refresh(fromCode: Int) {
        when (fromCode) {
            DEAD_RECKONING -> {
                this.xPos = mDeadReckoning.xPos
                this.yPos = mDeadReckoning.yPos
            }
            SIMULATE_POSITION -> {
                this.xPos = mSimulatePosition.xPos
                this.yPos = mSimulatePosition.yPos
            }
        }

        if (salaManager.ioTSelectionActivity != null) {
            with(salaManager.ioTSelectionActivity) {
                runOnUiThread {
                    this.refreshHumanButton(
                        salaManager.mButtonMaker.generateButton(
                            IoTData("HUMAN", "USER", "0.0.0.0", xPos, yPos)
                        )
                    )
                }
            }


        }

        debug { "${fromCode}로부터 현재 X 좌표 및 Y 좌표 갱신" }
    }
}