package com.example.flickrgallery.model

import com.google.gson.annotations.SerializedName

class Photo {

    val id: String = ""
    val farm: Int = -1
    @SerializedName("isfamily")
    val isFamily: Int = -1
    @SerializedName("isfriend")
    val isFriend: Int = -1
    @SerializedName("ispublic")
    val isPublic: Int = -1
    val owner: String = ""
    val secret: String = ""
    val server: String = ""
    val title: String = ""

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