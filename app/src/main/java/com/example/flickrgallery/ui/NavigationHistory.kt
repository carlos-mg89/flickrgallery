package com.example.flickrgallery.ui

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.util.*
import kotlin.NoSuchElementException

class NavigationHistory() : Parcelable {

    private var selectedPages: Deque<Int> = ArrayDeque(MAX_BOTTOM_DESTINATIONS)
    private var isBackPressed = false

    constructor(parcel: Parcel) : this() {
        isBackPressed = parcel.readByte() != 0.toByte()
        selectedPages = parcel.readSerializable() as ArrayDeque<Int>
    }

    fun pushItem(item: Int) {
        // remove if already was selected, move it to front
        if (selectedPages.contains(item)) selectedPages.remove(item)
        selectedPages.push(item)
        isBackPressed = false
    }

    fun popLastSelected(): Int {
        return try {
            if (selectedPages.size == 1 && !isBackPressed) {
                selectedPages.clear()
                DEF_ITEM
            } else if (selectedPages.size >= 2 && !isBackPressed) {
                isBackPressed = true
                selectedPages.pop()
                selectedPages.pop()
            } else {
                selectedPages.pop()
            }
        } catch (e: NoSuchElementException) {
            Log.e(TAG, "${e.message} cause ${e.cause}")
            DEF_ITEM
        }
    }

    fun isEmpty() = selectedPages.isEmpty()

    override fun toString(): String {
        return selectedPages.toString()
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeByte((if (isBackPressed) 1 else 0).toByte())
        parcel.writeSerializable(selectedPages as ArrayDeque<Int>)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NavigationHistory> {

        private const val DEF_ITEM = 0

        private const val TAG = "NavigationHistory"

        private const val MAX_BOTTOM_DESTINATIONS = 5

        override fun createFromParcel(parcel: Parcel): NavigationHistory {
            return NavigationHistory(parcel)
        }

        override fun newArray(size: Int): Array<NavigationHistory?> {
            return arrayOfNulls(size)
        }
    }
}