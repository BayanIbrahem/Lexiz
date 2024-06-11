package com.dev.bayan.ibrahim.core.common.jaar_exception.db

class JaArDbInputWordEditNullIdException() : JaArDbInputMissingFieldException(
    processTag = "input word",
    fieldName = "id",
    exceptionCause = "trying to update word with null id"
) {
}