package com.dev.bayan.ibrahim.core.common.jaar_exception.db

class JaArDbInputWordNameDescriptionException() : JaArDbInputMissingFieldException(
    processTag = "input word",
    fieldName = "name-description",
    exceptionCause = "both name and description is null"
) {
}