package com.sala.iotlab.sala_app.sala.helper


object SizeConverter {

    /**
     * [xDrawMax]와 [yDrawMax]를 그리고자 하는 이미지뷰의 너비, 높이로 받아서
     * [xActual]과 [yActual]을 해당 이미지뷰에 해당하는
     * x, y 좌표로 변환한 [Pair]를 반환
     */
    fun conversionSize(
        xDrawMax: Int, yDrawMax: Int, xActualMax: Int, yActualMax: Int, xActual: Int, yActual: Int,
        xDrawOffset: Int = 0, yDrawOffset: Int = 0,
        xDrawMin: Int = 0, yDrawMin: Int = 0,
        xActualMin: Int = 0, yActualMin: Int = 0
    ): Pair<Int, Int> {
        return Pair<Int, Int>(
            targetConversion(xDrawMax, xActual, xActualMax, xDrawOffset, xDrawMin, xActualMin),
            targetConversion(yDrawMax, yActual, yActualMax, yDrawOffset, yDrawMin, yActualMin)
        )
    }


    /**
     * 한 축의 변화만 해당하도록
     * [tarActual]을 [tarDrawMax]에 맞도록 변환하여 반환
     */
    fun targetConversion(
        tarDrawMax: Int, tarActual: Int, tarActualMax: Int,
        tarDrawOffset: Int = 0, tarDrawMin: Int = 0, tarActualMin: Int = 0
    ): Int {
        return conversionSingle(
            tarActual,
            tarActualMax - tarActualMin,
            tarDrawMax - tarDrawMin
        ) + tarDrawOffset
    }


    /**
     * [targetConversion]에 도움을 주기 위한 함수
     */
    private fun conversionSingle(originalVal: Int, originalMax: Int, targetMax: Int): Int {
        return (originalVal.toDouble() / originalMax.toDouble() * targetMax.toDouble()).toInt()
    }

}