package com.sala.iotlab.sala_app.Buildings

import android.content.res.Resources
import android.graphics.drawable.Drawable
import at.lukle.clickableareasimage.ClickableArea
import com.sala.iotlab.sala_app.R
import com.sala.iotlab.sala_app.data.BuildingData
import com.sala.iotlab.sala_app.data.RoomData

object SungKyunKwanUniversitySuwonCampus {
    private val lifeWorkout: ArrayList<BuildingData> = arrayListOf(
        BuildingData(1, "01", "삼성학술정보관", 0, null, null),
        BuildingData(1, "03", "학생회관", 0, null, null),
        BuildingData(1, "04", "복지회관", 0, null, null),
        BuildingData(1, "05", "수성관", 0, null, null),
        BuildingData(1, "11", "체육관", 0, null, null),
        BuildingData(1, "13", "대강당", 0, null, null)
    )
    private val engineering: ArrayList<BuildingData> = arrayListOf(
        BuildingData(2, "20", "공학실습동", 0, null, null),
        BuildingData(2, "21", "제1공학관", 0, null, null),
        BuildingData(2, "22", "제1공학관", 0, null, null),
        BuildingData(2, "23", "제1공학관", 0, null, null),
        BuildingData(2, "24", "공학실습동", 0, null, null),
        BuildingData(2, "25", "제2공학관", 0, null, null),
        BuildingData(2, "26", "제2공학관", 0, null, null),
        BuildingData(2, "27", "제2공학관", 0, null, null),
        BuildingData(2, "28", "공학실습동", 0, null, null),
        BuildingData(2, "30", "건축환경실습동", 0, null, null),
        BuildingData(2, "40", "반도체관", 0, null, null)
    )
    private val natural: ArrayList<BuildingData> = arrayListOf(
        BuildingData(3, "31", "제1과학관", 0, null, null),
        BuildingData(3, "32", "제2과학관", 0, null, null),
        BuildingData(3, "33", "화학관", 0, null, null),
        BuildingData(3, "51", "기초학문관", 0, null, null),
        BuildingData(3, "53", "약학관", 0, null, null),
        BuildingData(3, "61", "생명공학관", 0, null, null),
        BuildingData(3, "62", "생명공학관", 0, null, null),
        BuildingData(3, "63", "생명공학실습동", 0, null, null),
        BuildingData(3, "64", "생명공학실습동", 0, null, null),
        BuildingData(3, "71", "의학관", 0, null, null)
    )
    private val research: ArrayList<BuildingData> = arrayListOf(
        BuildingData(4, "81", "제1종합연구동", 0, null, null),
        BuildingData(4, "83", "제2종합연구동", 0, null, null),
        BuildingData(4, "84", "제약기술관", 0, null, null),
        BuildingData(
            4, "85", "산학협력센터", 7,
            arrayOf(
                R.mipmap.colab1st,
                R.mipmap.colab2nd,
                R.mipmap.colab3rd,
                R.mipmap.colab4th,
                R.mipmap.colab5th,
                R.mipmap.colab6th,
                R.mipmap.colab7th
            ), arrayOf(
                null, null, null, arrayListOf<ClickableArea<RoomData>>(
                    ClickableArea(485,350,34,17,RoomData("산학협력센터",85468, R.drawable.laboratory)
                    )
                ), null, null, null
            )
        ),
        BuildingData(4, "86", "N센터", 0, null, null)
    )
    private val dormitory: ArrayList<BuildingData> = arrayListOf(
        BuildingData(5, "91", "인관", 0, null, null),
        BuildingData(5, "92", "의관", 0, null, null),
        BuildingData(5, "93", "예관", 0, null, null),
        BuildingData(5, "95", "지관", 0, null, null),
        BuildingData(5, "96", "게스트하우스", 0, null, null),
        BuildingData(5, "97", "신관", 0, null, null)
    )
    private val etc: ArrayList<BuildingData> = arrayListOf(
        BuildingData(6, "102", "환경플랜트", 0, null, null),
        BuildingData(6, "103", "건축관리실", 0, null, null),
        BuildingData(6, "104", "파워플랜트", 0, null, null)
    )

    val BuildingCollection: MutableMap<String, BuildingData> = mutableMapOf()

    val BuildingInfo = arrayOf(
        lifeWorkout, engineering, natural, research, dormitory, etc
    )

    val BuildingRecycelrs = arrayOf(
        R.id.lifeworkout, R.id.engineering, R.id.natural, R.id.research, R.id.dormitory, R.id.etc
    )

    init {
        for (array in BuildingInfo) {
            for (data in array) {
                BuildingCollection.put(data.getBuildingNum(), data)
            }
        }
    }
}