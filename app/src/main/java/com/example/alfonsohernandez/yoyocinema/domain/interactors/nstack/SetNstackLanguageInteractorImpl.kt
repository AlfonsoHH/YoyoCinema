package com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack

import dk.nodes.nstack.kotlin.NStack
import java.util.*

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
class SetNstackLanguageInteractorImpl: SetNstackLanguageInteractor {
    override fun setDefaultLanguage(language: Locale) {
        NStack.language = language
    }
}