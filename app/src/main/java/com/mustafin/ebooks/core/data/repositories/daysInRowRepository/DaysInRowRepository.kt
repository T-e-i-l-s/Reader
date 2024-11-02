package com.mustafin.ebooks.core.data.repositories.daysInRowRepository

interface DaysInRowRepository {
    suspend fun getDaysInRowCount(): Int
    suspend fun updateDaysInRowCount()
}