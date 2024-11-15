package com.mustafin.ebooks.core.data.repositories.daysInRowRepository

import com.mustafin.ebooks.core.data.source.local.daysInRow.DaysInRowSource
import io.appmetrica.analytics.AppMetrica
import java.time.LocalDate
import javax.inject.Inject

// Репозиторий для фуникции "Дни в ударе"
class DaysInRowRepositoryImpl @Inject constructor(
    private val daysInRowSource: DaysInRowSource
) : DaysInRowRepository {
    override suspend fun getDaysInRowCount(): Int {
        val previousDate = daysInRowSource.getPreviousDate()
        val currentDate = LocalDate.now()
        if (previousDate != null && previousDate.isBefore(currentDate.minusDays(1))) {
            return 0
        }
        return daysInRowSource.getDaysInRowCount()
    }

    // Функция, которая увеличивает или сбрасывает счетчик дней в ударе в зависимости от даты
    override suspend fun updateDaysInRowCount() {
        var daysInRowCount = daysInRowSource.getDaysInRowCount()

        val previousDate = daysInRowSource.getPreviousDate()
        val currentDate = LocalDate.now()

        if (previousDate == null || previousDate == currentDate.minusDays(1)) {
            daysInRowCount++
        } else if (previousDate.isBefore(currentDate.minusDays(1))) {
            daysInRowCount = 1
        }

        daysInRowSource.updateDaysInRowCount(daysInRowCount)

        AppMetrica.reportEvent(
            "day_in_row",
            "{\"count\":\"${daysInRowCount + 1}\"}"
        )
    }
}