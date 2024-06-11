package com.dev.bayan.ibrahim.core.common.jaar_exception.db

open class JaArDbInputMissingFieldException(
    private val processTag: String,
    private val fieldName: String,
    private val exceptionCause: String,
) : JaArDbException() {
    open fun jaArErrorMessage(): String {
        return "JaAr db input missing field exception in ($processTag), exception when input field" +
                "$fieldName ($exceptionCause)"
    }
}