package com.mustafin.ebooks.mainFlow.data.source.local

import android.content.SharedPreferences
import javax.inject.Inject

class RulesSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val ARE_ACCEPTED_CELL = "rules_are_accepted"
    }

    fun getAreAccepted(): Boolean {
        return sharedPreferences.getBoolean(ARE_ACCEPTED_CELL, false)
    }

    fun setAreAccepted(areAccepted: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(ARE_ACCEPTED_CELL, areAccepted)
            apply()
        }
    }
}