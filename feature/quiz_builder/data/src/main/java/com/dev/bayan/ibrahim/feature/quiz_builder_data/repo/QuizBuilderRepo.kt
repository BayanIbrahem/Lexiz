package com.dev.bayan.ibrahim.feature.quiz_builder_data.repo

import com.dev.bayan.ibrahim.core.data.repo.JaArCurrentLocaleRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index.QuizBuilderNewFilterNameIndexRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index.QuizBuilderNewGroupNameIndexRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.name_index.QuizBuilderNewQuizNameIndexRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawAllCategoriesRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawAllLanguagesRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawAllTypesRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawFailurePeakRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawLengthPeakRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawSelectedLanguageCategoriesRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawSelectedLanguageTypesRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.raw.QuizDbRawSuccessPeakRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.delete.QuizDbSetupDeleteFilterRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.delete.QuizDbSetupDeleteGroupRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.get.QuizDbSetupTemplateFilterRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.get.QuizDbSetupTemplateGroupRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.get.QuizDbSetupTemplateQuizRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.upsesrt.QuizDbSetupUpsertFilterRepo
import com.dev.bayan.ibrahim.feature.quiz_builder_data.repo.components.template.upsesrt.QuizDbSetupUpsertGroupRepo

interface QuizBuilderRepo :
    QuizDbRawAllLanguagesRepo,
    QuizDbRawAllTypesRepo,
    QuizDbRawAllCategoriesRepo,

    QuizDbRawSelectedLanguageTypesRepo,
    QuizDbRawSelectedLanguageCategoriesRepo,

    QuizDbRawLengthPeakRepo,
    QuizDbRawFailurePeakRepo,
    QuizDbRawSuccessPeakRepo,

    QuizDbSetupTemplateFilterRepo,
    QuizDbSetupTemplateGroupRepo,
    QuizDbSetupTemplateQuizRepo,

    QuizDbSetupUpsertFilterRepo,
    QuizDbSetupUpsertGroupRepo,

    QuizDbSetupDeleteFilterRepo,
    QuizDbSetupDeleteGroupRepo,

    QuizBuilderNewQuizNameIndexRepo,
    QuizBuilderNewGroupNameIndexRepo,
    QuizBuilderNewFilterNameIndexRepo,

    JaArCurrentLocaleRepo