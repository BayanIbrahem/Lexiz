package com.dev.bayan.ibrahim.core.database.entities.relation.quiz

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dev.bayan.ibrahim.core.database.entities.relation.filter.GroupCrossFilterRelation
import com.dev.bayan.ibrahim.core.database.entities.table.FilterGroupEntity
import com.dev.bayan.ibrahim.core.database.entities.table.QuizCrossFilterEntity
import com.dev.bayan.ibrahim.core.database.entities.table.QuizCrossWordEntity
import com.dev.bayan.ibrahim.core.database.entities.table.QuizEntity
import com.dev.bayan.ibrahim.core.database.entities.table.WordEntity
import com.dev.bayan.ibrahim.core.database.utils.DB_NAMES


data class QuizCrossFilterWordsRelation(
    @Embedded val quiz: QuizEntity,
    @Relation(
        parentColumn = DB_NAMES.QUIZ.id,
        entityColumn = DB_NAMES.FILTER_GROUP.id,
        entity = FilterGroupEntity::class,
        associateBy = Junction(
            value = QuizCrossFilterEntity::class,
            parentColumn = DB_NAMES.QUIZ_CROSS_FILTER.quiz_id,
            entityColumn = DB_NAMES.QUIZ_CROSS_FILTER.filter_group_id,
        )
    ) val filterGroups: List<GroupCrossFilterRelation>,
    @Relation(
        parentColumn = DB_NAMES.QUIZ.id,
        entityColumn = DB_NAMES.WORD.id,
        entity = WordEntity::class,
        associateBy = Junction(
            value = QuizCrossWordEntity::class,
            parentColumn = DB_NAMES.QUIZ_CROSS_WORD.quiz_id,
            entityColumn = DB_NAMES.QUIZ_CROSS_WORD.word_id,
        )
    ) val words: List<WordEntity>,
)

