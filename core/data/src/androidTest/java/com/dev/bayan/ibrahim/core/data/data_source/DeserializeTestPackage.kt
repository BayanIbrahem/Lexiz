package com.dev.bayan.ibrahim.core.data.data_source

import com.dev.bayan.ibrahim.core.data.model.LibraryLanguagesPackage
import com.dev.bayan.ibrahim.core.data.model.LibraryPackageLanguage
import com.dev.bayan.ibrahim.core.data.model.LibraryPackageType

val deserializedTestPackage = LibraryLanguagesPackage(
    name = "test package",
    id = 1,
    version = 1,
    key = "1234",
    languages = listOf(
        LibraryPackageLanguage(
            version = 1,
            language_code = "en",
            interchangeables = listOf(
                listOf('a', 'á', 'ä'),
                listOf('e', 'è', 'é')
            ),
            ignorables = listOf('-', '\''),
            valid_chars = listOf(
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'á', 'ä', 'è', 'é'
            ),
            types = listOf(
                LibraryPackageType(
                    version = 1,
                    id = 1,
                    language_code = "en",
                    name = "Noun",
                    description = "Noun Description",
                )
            )
        ),
        LibraryPackageLanguage(
            version = 1,
            language_code = "ar",
            interchangeables = listOf(
                listOf('ا', 'آ', 'أ', 'إ'),
                listOf('ء', 'أ', 'إ', 'ؤ', 'ئ')
            ),
            ignorables = listOf('ـ', 'ً', 'ٍ', 'ٌ', 'ّ', 'ْ', 'َ', 'ُ', 'ِ'),
            valid_chars = listOf(
                'ا', 'ب', 'ت', 'ث', 'ج', 'ح', 'خ', 'د', 'ذ', 'ر', 'ز', 'س', 'ش',
                'ص', 'ض', 'ط',
                'ظ', 'ع', 'غ', 'ف', 'ق', 'ك', 'ل', 'م', 'ن', 'ه', 'و', 'ي', 'ء', 'آ', 'أ', 'إ', 'ؤ',
                'ئ', 'ة', 'ـ', 'ً', 'ٍ', 'ٌ', 'ّ', 'ْ', 'َ', 'ُ', 'ِ'
            ),
            types = listOf(
                LibraryPackageType(
                    version = 1,
                    id = 17,
                    language_code = "ar",
                    name = "اسم",
                    description = "وصف الاسم",
                )
            )
        ),
    )
)