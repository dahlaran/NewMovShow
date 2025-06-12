package com.dahlaran.newmovshow.data.local

import androidx.room.TypeConverter
import com.dahlaran.newmovshow.domain.model.SeasonList
import com.dahlaran.newmovshow.domain.model.VideoInfo
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listStringToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListString(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun seasonListToJson(value: SeasonList?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToSeasonList(value: String) = Gson().fromJson(value, SeasonList::class.java)

    @TypeConverter
    fun videoListToJson(value: VideoInfo) = Gson().toJson(value)

    @TypeConverter
    fun jsonToVideoInfo(value: String) = Gson().fromJson(value, VideoInfo::class.java)
}