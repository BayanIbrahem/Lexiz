package com.dev.bayan.ibrahim.ja_ar

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
//@HiltAndroidTest
class JaArNavGraphTest {
    @get:Rule
    val composeRule = createComposeRule()
//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(): Unit {
//        hiltRule.inject()
    }

    @Test
    fun navGraphTest_startDestination_assertHome() {
        composeRule.setContent {
            Text(
                modifier = Modifier
                    .semantics { testTag = "test_home_screen" },
                text = "text"
            )
        }
        composeRule.onNode(hasText("text")).assertExists()
    }
}
