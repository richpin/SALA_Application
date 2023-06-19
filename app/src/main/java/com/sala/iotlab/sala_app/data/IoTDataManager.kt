package com.sala.iotlab.sala_app.data

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
/**
 * IoTData 클래스를 관리하기 위한 싱글톤
 * @author 유영석
 */
object IoTDataManager : AnkoLogger {

    const val NOT_DNS: Int = 0xA001
    const val DNS_BUT_NO_POS: Int = 0xA002
    const val DNS_WITH_POS: Int = 0xA003

    private const val DNS_OFFSET_X: Int = 6
    private const val DNS_OFFSET_Y: Int = 5
    private const val DNS_OFFSET_TYPE: Int = 14


    /**
     * [dnsName]이 유효한 DNS 이름일 경우 [DNS_WITH_POS] 반환
     */
    fun checkDNSName(dnsName: String): Int {
        val tokens = dnsName.split("\\.").toTypedArray()
        val size = tokens.size

        if (size < 2) {
            return NOT_DNS
        }

        try {
            Integer.parseInt(tokens[size - DNS_OFFSET_X])
            Integer.parseInt(tokens[size - DNS_OFFSET_Y])
        } catch (e: Exception) {
            return DNS_BUT_NO_POS
        }

        return DNS_WITH_POS
    }


    /**
     * [dnsName]을 순수 DNS Name 으로 변환하여 반환
     * 변환 불가능할 경우 [dnsName] 그대로를 반환
     */
    fun getPureDNSName(dnsName: String): String {
        if (checkDNSName(dnsName) != DNS_WITH_POS) return dnsName

        val tokens = dnsName.split("\\.").toTypedArray()
        val size = tokens.size
        val sb = StringBuilder()

        tokens[size - DNS_OFFSET_X] = "X"
        tokens[size - DNS_OFFSET_Y] = "Y"

        for (st in tokens) {
            sb.append(st)
        }

        return sb.toString()
    }


    /**
     * DNS 이름이 [dnsName]이고 IP 주소가 [ip]인 새로운 [IoTData] 객체를 반환
     */
    fun generateIoTData(dnsName: String, ip: String): IoTData {
        val regex = "\\.".toRegex()
        val tokens: List<String> = regex.split(dnsName).toMutableList()
        val size = tokens.size
        val pos_x = Integer.parseInt(tokens[size - DNS_OFFSET_X])
        val pos_y = Integer.parseInt(tokens[size - DNS_OFFSET_Y])

        val type = formatType(tokens[size - DNS_OFFSET_TYPE].toUpperCase())

        debug { "IoTData가 생성됨 -> 타입 : ${type} , DNS 이름 : ${dnsName} , IP 주소 : ${ip} , X좌표 : ${pos_x} , Y좌표 : ${pos_y}" }

        return IoTData(type, dnsName, ip, pos_x, pos_y)
    }

    /**
     * 타입을 나타내는 Raw 한 [tarStr]을 정형화된 형식으로 변환하여 반환
     */
    private fun formatType(tarStr: String): String {
        with(tarStr) {
            return when {
                contains("TV") -> "TV"
                contains("AIR") -> "AIRCONDITIONER"
                contains("LED") || contains("LIGHT") -> "LIGHT"
                contains("TEMPERATURE") -> "TEMPERATURE"
                contains("STOVE") || contains("GASRANGE") -> "GASSTOVE"
                contains("CCTV") -> "CCTV"
                contains("SPEAKER") || contains("SOUND") -> "SPEAKER"
                else -> tarStr
            }
        }
    }
}