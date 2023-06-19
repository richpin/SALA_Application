package com.sala.iotlab.sala_app.sala.connect

import android.content.Context
import android.os.Handler
import android.os.Message
import com.sala.iotlab.sala_app.activity.IoTSelectionActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.io.DataInputStream
import java.io.IOException
import java.net.ConnectException
import java.net.Socket

object NetWorking : AnkoLogger {
    private val MSG_UPDATE_DNSINFORM = 0x1001
    private val ip = "192.168.4.1" // IP
    private val port = 5000

    class NetworkResultHandler : Handler()
    {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_UPDATE_DNSINFORM -> {
                    val dns_inform = msg.obj as String
                    (IoTSelectionActivity.mContext as IoTSelectionActivity).newIoTData(dns_inform)
                }
            }
        }
    }

    class NetworkThread(private val networkResultHandler: NetworkResultHandler) : Thread() {
        override fun run() {
            while (true) {
                try {
                    // 소켓을 생성하여 연결을 요청한다.
                    debug { "SALA 서버와 연결 요청" }
                    val socket = Socket(ip, port)
                    val input = socket.getInputStream()
                    val dis = DataInputStream(input)

                    val k = dis.readUTF()
                    dis.close()
                    socket.close()

                    val msg = networkResultHandler.obtainMessage()
                    msg.what = MSG_UPDATE_DNSINFORM
                    msg.obj = k
                    networkResultHandler.sendMessage(msg)

                } catch (ce: ConnectException) {
                    ce.printStackTrace()
                } catch (ie: IOException) {
                    ie.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                try {
                    Thread.sleep(2000)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
}
