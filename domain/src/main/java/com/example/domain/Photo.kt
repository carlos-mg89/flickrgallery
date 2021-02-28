package com.example.domain

import java.io.Serializable
import java.util.*


class Photo: Serializable {

    var id: String = ""
    var farm: Int = -1
    var family: Int = -1
    var friend: Int = -1
    var publicLicense: Int = -1
    var owner: String = ""
    var secret: String = ""
    var server: String = ""
    var title: String = ""
    var latitude: String = ""
    var longitude: String = ""
    var observation: String = ""
    var isSaved = false
    var savedDate: Date = Calendar.getInstance().time

    companion object {
        const val MEDIUM_640_IMAGE_SUFFIX = "_z.jpg";
        private const val FLICKR_URL_PREFIX = "https://farm"
        private const val FLICKR_URL_BASE_DOMAIN = ".staticflickr.com/"
    }

    fun getMedium640Url(): String {
        return getBaseImageUrl().toString() + MEDIUM_640_IMAGE_SUFFIX
    }

    private fun getBaseImageUrl(): StringBuffer {
        val buffer = StringBuffer()
        buffer.append("$FLICKR_URL_PREFIX${farm}$FLICKR_URL_BASE_DOMAIN${server}/${id}_${secret}")
        return buffer
    }
}
