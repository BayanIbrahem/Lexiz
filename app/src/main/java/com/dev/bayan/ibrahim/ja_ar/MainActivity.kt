package com.dev.bayan.ibrahim.ja_ar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.dev.bayan.ibrahim.core.ui.components.notification.item.JaArNotificationHostPreview
import com.dev.bayan.ibrahim.core.ui.theme.JaArTheme
import com.dev.bayan.ibrahim.ja_ar.window_size_class.jaArCalculateWindowSizeClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
//         JaArTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val windowSizeClass = jaArCalculateWindowSizeClass(this)
//                    val displayFeatures = calculateDisplayFeatures(this)
                JaArApp(
                    widthSizeClass = windowSizeClass.widthSizeClass,
                    heightSizeClass = windowSizeClass.heightSizeClass
                )
            }
//         }
        }
    }
}
