package com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack

import java.util.*

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
interface GetNStackLanguageInteractor {
    fun addLanguageChangeListener(listener: (Locale) -> Unit)
    fun getCurrentLanguage(listener: (Locale) -> Unit)
}