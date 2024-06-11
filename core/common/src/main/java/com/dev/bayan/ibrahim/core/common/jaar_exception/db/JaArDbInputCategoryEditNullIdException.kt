package com.dev.bayan.ibrahim.core.common.jaar_exception.db

class JaArDbInputCategoryEditNullIdException() : JaArDbInputMissingFieldException(
    processTag = "input category",
    fieldName = "id",
    exceptionCause = "trying to update category with null id"
) {
}