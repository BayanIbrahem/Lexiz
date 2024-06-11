package com.dev.bayan.ibrahim.core.common.model

const val INVALID_ID: Long = -1

interface ModelItem {
    val id: Long get() = INVALID_ID
    val value: String
    val subtitle: String? get() = null
    val languageCode: String? get() = null
}