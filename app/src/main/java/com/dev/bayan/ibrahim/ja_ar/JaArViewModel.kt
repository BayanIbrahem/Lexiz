package com.dev.bayan.ibrahim.ja_ar

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.bayan.ibrahim.core.data.utils.deserializePackageFromStream
import com.dev.bayan.ibrahim.core.database.db.JaArDatabase
import com.dev.bayan.ibrahim.core.database.utils.toLanguageTypeEntities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JaArViewModel @Inject constructor(
    private val db: JaArDatabase,
) : ViewModel() {

    fun initDbIfEmpty(context: Context, success: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val langDao = db.getLanguageDao()
            if (!langDao.isDbContainsAnyLanguage()) {
                val languagesTypesPair = deserializePackageFromStream(
                    context.assets.open("initial_package.json")
                )?.toLanguageTypeEntities()
                if (languagesTypesPair != null) {
                    langDao.insertOrUpdateIfNewerVersionAllLanguages(languagesTypesPair.first)
                    db.getTypeDao().insertOrIgnoreAllTypes(languagesTypesPair.second)
                    success(true)
                } else {
                    success(false)
                    Log.e("deserialize", "can not deserialize init package")
                }
            }
        }
    }
}