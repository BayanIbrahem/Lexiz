package com.dev.bayan.ibrahim.core.common.jaar_exception.db

class JaArDbInputWordCategoryException() : JaArDbInputMissingFieldException(
    processTag = "input word",
    fieldName = "category",
    exceptionCause = "both category id and category data are null"
) {
}