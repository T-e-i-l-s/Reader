package com.mustafin.ebooks.mainFlow.data.repositories.rulesRepository

interface RulesRepository {
    fun getAreAccepted(): Boolean
    fun setAreAccepted()
}