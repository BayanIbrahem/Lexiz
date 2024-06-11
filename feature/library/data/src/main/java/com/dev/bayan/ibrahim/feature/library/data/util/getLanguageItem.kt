package com.dev.bayan.ibrahim.feature.library.data.util

import com.dev.bayan.ibrahim.core.common.model.LanguageItem
import com.dev.bayan.ibrahim.core.data.repo.JaArCurrentLocaleRepo

fun JaArCurrentLocaleRepo.getLanguageItem(code: String): LanguageItem {
    return LanguageItem(
        languageCode = code,
        selfDisplayName = getLanguageSelfDisplayName(code),
        localDisplayName = getLanguageLocalDisplayName(code)
    )
}