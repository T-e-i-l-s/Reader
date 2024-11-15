package com.mustafin.ebooks.mainFlow.data.repositories.rulesRepository

import com.mustafin.ebooks.mainFlow.data.source.local.RulesSource
import io.appmetrica.analytics.AppMetrica
import javax.inject.Inject

/* Репозиторий для работы с данными о пользовательских соглашениях */
class RulesRepositoryImpl @Inject constructor(
    private val rulesSource: RulesSource
): RulesRepository {
    override fun getAreAccepted(): Boolean {
        return rulesSource.getAreAccepted()
    }

    override fun setAreAccepted() {
        rulesSource.setAreAccepted(true)

        AppMetrica.reportEvent("rules_are_accepted")
    }
}