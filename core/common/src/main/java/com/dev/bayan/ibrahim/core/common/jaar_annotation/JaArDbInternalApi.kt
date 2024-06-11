package com.dev.bayan.ibrahim.core.common.jaar_annotation


/**
 * this function is NOT usable as api, it is used inside other [JaArDbPublicApi] functions which will be mentioned
 * in its documentation
 * @see JaArDbPublicApi
 * @suppress This function is part of the JaAr database internal API.
 * It is not recommended to use internal APIs, it may cause problems if the called carelessly
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class JaArDbInternalApi()
