package com.dev.bayan.ibrahim.core.database.utils

//internal const val db_name: String = "jaar_db"
//internal const val db_version: Int = 1
//internal const val db_lang_table: String = "${db_name}_languages"
//internal const val db_lang_code = "${db_lang_table}_code"
//internal const val db_lang_validChars = "${db_lang_table}_validCharacters"
//internal const val db_lang_ignorableSearchChars = "${db_lang_table}_searchIgnorableCharacters"
//internal const val db_category_table = "${DB_NAMES.db}_categories"
//internal const val db_category_id = "${db_category_table}_id"
//internal const val db_category_name = "${db_category_table}_name"
//internal const val db_category_description = "${db_category_table}_description"
//internal const val db_category_addDate = "${db_category_table}_addDate"
//internal const val db_category_lastEditDate = "${db_category_table}_lastEditDate"
/**
 * this data object contains information for database, tables, column names for every part in the database
 * any table name is from format: ${db}-${tableChosenName} also any column name format is from type
 * ${table}-${columnChosenName}, db and tableChosenName and columnChosenName are camelCaseNamesFormat
 * example: db = "db", table = "tbName", column = "clName" so in database this column name will be
 * "db_tbName_clName", which is easy in debugging and ensure avoiding any conflict
 * @property version version of this db
 * @property db name of this db
 */
internal data object DB_NAMES {
    const val version = 2
    const val db = "jaarDb"

    /** @see DB_NAMES */
    data object LANGUAGE {
        const val table = "${db}_languages"
        const val code = "${table}_code"
        const val version = "${table}_versoin"
        const val valid_characters = "${table}_validCharacters"
        const val search_ignorable_characters = "${table}_searchIgnorableCharacters"
        const val interchangable_characters = "${table}_interchangableCharacters"
        const val package_id = "${table}_packageId"
        const val package_name = "${table}_packageName"
        const val package_version = "${table}_packageVersion"
        const val package_key = "${table}_packageKey"
    }

    /** @see DB_NAMES */
    data object CATEGORY {
        const val table = "${db}_categories"
        const val id = "${table}_id"
        const val name = "${table}_name"
        const val description = "${table}_description"
        const val add_date = "${table}_addDate"
        const val last_edit_date = "${table}_lastEditDate"
    }

    /** @see DB_NAMES */
    data object MEANING {
        const val table = "${db}_meanings"
        const val id = "${table}_id"
    }

    /** @see DB_NAMES */
    data object WORD {
        const val table = "${db}_words"
        const val add_date = "${table}_addDate"
        const val description = "${table}_description"
        const val language_code = "${table}_language"
        const val last_edit_date = "${table}_lastEditDate"
        const val meaning_id = "${table}_meaningId"
        const val name = "${table}_name"
        const val type_id = "${table}_typeId"
        const val id = "${table}_id"
    }

    /** @see DB_NAMES */
    data object TYPE {
        const val table = "${db}_types"
        const val id = "${table}_id"
        const val name = "${table}_name"
        const val description = "${table}_description"
        const val language = "${table}_language"
        const val type_version = "${table}_typeVersion"
    }

    /** @see DB_NAMES */
    data object QUIZ {
        const val table = "${db}_quizzes"
        const val id = "${table}_id"
        const val name = "${table}_name"
        const val is_rule_store_type = "${table}_isRuleStoreType"
        const val parts = "${table}_parts"
        const val last_applied_part = "${table}_lastAppliedPart"

        const val is_one_time_execution = "${table}_isOneTimeExecution"
        const val first_language = "${table}_firstLanguage"
        const val second_language = "${table}_secondLanguage"
        const val max_word_time = "${table}_maxWordTime"

        const val next_apply_date = "${table}_nextApplyDate"
        const val last_apply_date = "${table}_lastApplyDate"
        const val applied_times_count = "${table}_appliedTimes"

        /** @see QUIZ */
        data object DEF_VALUES {
            const val is_rule_store_type = "true"
            const val parts = "1"
            const val last_applied_part = "-1"

            const val is_one_time_execution = "false"
            const val max_word_time = "60"

            const val next_apply_date = "null"
            const val last_apply_date = "null"
            const val applied_times_count = "0"
        }
    }

    /** @see DB_NAMES */
    data object FILTER_GROUP {
        const val table = "${db}_filterGroups"
        const val id = "${table}_id"
        const val name = "${table}_name"
        const val template = "${table}_isSaved"
    }

    /** @see DB_NAMES */
    data object FILTER {
        const val table = "${db}_filters"
        const val id = "${table}_id"
        const val name = "${table}_name"
        const val type = "${table}_type"
        const val primary_value = "${table}_primaryValue"
        const val secondary_value = "${table}_secondaryValue"
        const val tertiary_value = "${table}_tertiaryValue"
        const val template = "${table}_isSaved"
    }

    /** @see DB_NAMES */
    data object FILTER_CROSS_GROUP {
        const val table = "${db}_groupCrossFilter"
        const val group_id = "${table}_groupId"
        const val filter_id = "${table}_filterId"
    }

    /** @see DB_NAMES */
    data object QUIZ_CROSS_FILTER {
        const val table = "${db}_quizCrossFilter"
        const val quiz_id = "${table}_quizId"
        const val filter_group_id = "${table}_filterGroupId"
    }

    /** @see DB_NAMES */
    data object WORD_RESULT {
        const val table = "${db}_wordResult"
        const val id = "${table}_id"
        const val result_id = "${table}_resultId"
        const val word_id = "${table}_wordId"
        const val is_failed = "${table}_isFailed"
        const val date = "${table}_date"
    }

    /** @see DB_NAMES */
    data object RESULT {
        const val table = "${db}_result"
        const val id = "${table}_id"
        const val score = "${table}_score"
        const val date = "${table}_date"
        const val first_language = "${table}_firstLanguage"
        const val second_language = "${table}_secondLanguage"
        const val words_count = "${table}_wordsCount"
        const val quiz_id = "${table}_quizId"
        const val quiz_name = "${table}_quizName"
    }

    /** @see DB_NAMES */
    data object FILTER_CROSS_CATEGORY {
        const val table = "${db}_categoryCrossFilter"
        const val category_id = "${table}_categoryId"
        const val filter_id = "${table}_filterId"
    }

    /** @see DB_NAMES */
    data object FILTER_CROSS_TYPE {
        const val table = "${db}_typeCrossFilter"
        const val type_id = "${table}_typeId"
        const val filter_id = "${table}_filterId"
    }

    /** @see DB_NAMES */
    data object QUIZ_CROSS_WORD {
        const val table = "${db}_quizWithWord"
        const val quiz_id = "${table}_quizId"
        const val word_id = "${table}_wordId"
    }

    data object WORD_CROSS_CATEGORY {
        const val table = "${db}_wordCrossCategory"
        const val word_id = "${table}_wordId"
        const val category_id = "${table}_categoryId"
    }
}
