package com.dev.bayan.ibrahim.feature.quiz_builder.domain.model.filter

/**
 * represent the filter functionality effect
 * values order is sored by restriction level accidentally
 * @property BYPASS opposite of [BARRIER] similar to [SHALLOW_BYPASS] that means this filter does no thing:
 * - like if i need to filter my content by a filter with condition like "select valid english word that
 * contains at least 0 character" so **FOR SURE** any input will by pass this filter
 * @property SHALLOW_BYPASS opposite of [SHALLOW_BARRIER] similar to [BYPASS] but the difference that is not effective only for current
 * data set which the filter is applied on
 * - like if i need only words what at most 12 characters but currently in my database all content
 * is for sure by activating moment is less than 12 character, but **maybe** later i would add a word
 * that its length is 15
 * @property ACTIVE that means this is a normal filter and functional filter:
 * - like if i need to filer english words by a filter with condition like "select any words that
 * it's length is at most 3" so **SOME** input will pass and some will not
 * @property SHALLOW_BARRIER  opposite of [SHALLOW_BYPASS] similar to [BARRIER] that means that this filter will
 * not validate any input **for the current dataset**
 * - like if i need only words what at least 12 characters but currently in my database all content
 * is for sure by filter activating moment is less than 12 character, but **maybe** later i would add a word
 * that its length is 15
 * @property BARRIER opposite of [BYPASS] similar to [SHALLOW_BARRIER] that means that this filter **FOR SURE** doesn't
 * pass any input
 * - like if i need filter some content with a filter that it's rule is "select any word that is
 * shorter than 3 chars and larger that 5 chars" so the valid set of words which bypass this filter is
 * empty
 */
enum class FilterEffect {
    /**
     *  opposite of [BARRIER] similar to [SHALLOW_BYPASS] that means this filter does no thing:
     * - like if i need to filter my content by a filter with condition like "select valid english word that
     * contains at least 0 character" so **FOR SURE** any input will by pass this filter
     */
    BYPASS,

    /**
     * opposite of [SHALLOW_BARRIER] similar to [BYPASS] but the difference that is not effective only for current
     * data set which the filter is applied on
     * - like if i need only words what at most 12 characters but currently in my database all content
     * is for sure by activating moment is less than 12 character, but **maybe** later i would add a word
     * that its length is 15
     */
    SHALLOW_BYPASS,

    /**
     * that means this is a normal filter and functional filter:
     * - like if i need to filer english words by a filter with condition like "select any words that
     * it's length is at most 3" so **SOME** input will pass and some will not
     */
    ACTIVE,

    /**
     * opposite of [SHALLOW_BYPASS] similar to [BARRIER] that means that this filter will
     * not validate any input **for the current dataset**
     * - like if i need only words what at least 12 characters but currently in my database all content
     * is for sure by filter activating moment is less than 12 character, but **maybe** later i would add a word
     * that its length is 15
     */
    SHALLOW_BARRIER,

    /**
     * opposite of [BYPASS] similar to [SHALLOW_BARRIER] that means that this filter **FOR SURE** doesn't
     * pass any input
     * - like if i need filter some content with a filter that it's rule is "select any word that is
     * shorter than 3 chars and larger that 5 chars" so the valid set of words which bypass this filter is
     * empty
     */
    BARRIER;

    val isBarrier: Boolean get() = this == BARRIER || this == SHALLOW_BARRIER
    val isBypass: Boolean get() = this == BYPASS || this == SHALLOW_BYPASS
}
