package com.dev.bayan.ibrahim.core.data.repo

import java.util.Locale

interface JaArCurrentLocaleRepo {
    val currentLocale: Locale
        get() = Locale.getDefault()
    val currentLanguageCode: String
        get() = currentLocale.language
    val currentLanguageDisplayName: String
        get() = currentLocale.getDisplayLanguage(currentLocale)

    fun getLanguageLocalDisplayName(code: String): String {
        return Locale(code).getDisplayLanguage(currentLocale)
    }

    fun getLanguageSelfDisplayName(code: String): String {
        val codeLocale = Locale(code)
        return codeLocale.getDisplayLanguage(codeLocale)
    }
}
