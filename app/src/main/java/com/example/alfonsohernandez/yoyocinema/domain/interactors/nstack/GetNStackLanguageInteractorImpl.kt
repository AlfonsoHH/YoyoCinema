package com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack

import dk.nodes.nstack.kotlin.NStack
import java.util.*

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
class GetNStackLanguageInteractorImpl: GetNStackLanguageInteractor {
    override fun addLanguageChangeListener(listener: (Locale) -> Unit) {
        NStack.addLanguageChangeListener(listener)
    }

    override fun getCurrentLanguage(listener: (Locale) -> Unit) {
        val language = NStack.language
        listener(language)
    }
}