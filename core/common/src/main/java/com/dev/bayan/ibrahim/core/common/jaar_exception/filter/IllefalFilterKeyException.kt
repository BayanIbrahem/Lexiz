package com.dev.bayan.ibrahim.core.common.jaar_exception.filter

class IllegalFilterKeyException(key: Int) : IllegalArgumentException(
    "invalid filter key $key"
)