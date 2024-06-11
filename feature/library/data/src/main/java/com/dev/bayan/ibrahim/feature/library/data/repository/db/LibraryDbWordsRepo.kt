package com.dev.bayan.ibrahim.feature.library.data.repository.db

import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.words.delete.*
import com.dev.bayan.ibrahim.feature.library.data.repository.db.components.words.get.*

interface LibraryDbWordsRepo :
    LibraryDbWordsGetRepo,
    LibraryDbWordsGetBaseDataSetRepo,
    LibraryDbWordDeleteRepo


