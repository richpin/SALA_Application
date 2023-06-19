package com.sala.iotlab.sala_app.sala.connect

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.io.IOException
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.MalformedURLException
import java.net.URL
import kotlin.Exception as Exception1

/**
 * SALA 서버 및 DNSNA 서버와 통신
 * @author 강성민
 */
object HttpConnection : AnkoLogger {
    private const val UTF_8 = "UTF-8"
    private val CHARSET_UTF_8 = Charsets.UTF_8
    private const val METHOD_GET = "GET"
    private const val METHOD_PUT = "PUT"


    /**
     * [url]과 HTTP GET 통신 수행하여 결과값 반환(Nullable)
     */
    fun getRequest(url: String): String? {
        try {
            with(URL(url).openConnection() as HttpURLConnection) {
                setProperties(this, METHOD_GET)
                if (responseCode == HTTP_OK) {
                    return inputStream.bufferedReader(CHARSET_UTF_8).use {
                        it.readText()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    /**
     * [url]에 [params]로 HTTP PUT 통신 수행하여 및 결과값 반환(Nullable)
     */
    fun putRequest(url: String, params: String): String? {
        try {
            with(URL(url).openConnection() as HttpURLConnection) {
                setProperties(this, METHOD_PUT)
                with(outputStream) {
                    write(params.toByteArray(CHARSET_UTF_8))
                    flush()
                }
                if (responseCode == HTTP_OK) {
                    return inputStream.bufferedReader(CHARSET_UTF_8).use {
                        it.readText()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    /**
     * [targetConnection]의 RequestMethod 를 [targetMethod]로 설정하고
     * Request Property 를 [UTF8] 로 설정함
     */
    private fun setProperties(targetConnection: HttpURLConnection, targetMethod: String) {
        with(targetConnection) {
            requestMethod = targetMethod
            setRequestProperty("Accept", "*/*") // Accept-Charset 설정.
            setRequestProperty(
                "Context_Type",
                "application/x-www-form-urlencoded;charset=$UTF_8"
            )
        }
    }
}