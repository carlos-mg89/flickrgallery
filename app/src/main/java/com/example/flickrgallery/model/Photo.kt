package com.example.flickrgallery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "photos_table")
class Photo {

    @PrimaryKey
    var id: String = ""

    var farm: Int = -1

    @SerializedName("isfamily")
    @ColumnInfo(name = "family")
    var family: Int = -1

    @SerializedName("isfriend")
    @ColumnInfo(name = "friend")
    var friend: Int = -1

    @SerializedName("ispublic")
    @ColumnInfo(name = "public")
    var publicLicense: Int = -1

    var owner: String = ""

    var secret: String = ""

    var server: String = ""

    var title: String = ""

    var latitude: String = ""

    var longitude: String = ""

    var observation: String = ""

    @ColumnInfo(name = "is_saved")
    var isSaved = false

    @ColumnInfo(name = "saved_date")
    var savedDate: Date = Calendar.getInstance().time


    companion object {
        const val MEDIUM_640_IMAGE_SUFFIX = "_z.jpg";
        private const val FLICKR_URL_PREFIX = "https://farm"
        private const val FLICKR_URL_BASE_DOMAIN = ".staticflickr.com/"
    }

    fun getMedium640Url(): String {
        return getBaseImageUrl().toString() + MEDIUM_640_IMAGE_SUFFIX
    }

    private fun getBaseImageUrl(): StringBuffer? {
        val buffer = StringBuffer()
        buffer.append("$FLICKR_URL_PREFIX${farm}$FLICKR_URL_BASE_DOMAIN${server}/${id}_${secret}")
        return buffer
    }
}