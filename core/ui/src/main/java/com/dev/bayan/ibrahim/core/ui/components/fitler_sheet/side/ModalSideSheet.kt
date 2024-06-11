package com.dev.bayan.ibrahim.core.ui.components.fitler_sheet.side


import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewRootForInspector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.popup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.dev.bayan.ibrahim.core.ui.R
import com.dev.bayan.ibrahim.core.ui.components.action.JaArAction
import com.dev.bayan.ibrahim.core.ui.components.dynamic.JaArDynamicString
import com.dev.bayan.ibrahim.core.ui.utils.animation.animFilterSheet
import java.util.UUID
import kotlin.math.roundToInt


/**
 * this is template for modal side sheet according to m3 design
 * @param modifier modifier passed to content of side sheet (with title and actions)
 * @param visible if the bottom sheet is visible or not it must be triggered as a state,
 * change value in [onDismissRequest] to false, and set it true on showing action,
 * @param title title of sheet, it is at most 1 line so it must be brief.
 * @param primaryAction the primary (first) action of this sheet usually is a [JaArAction.Clickable.Normal]
 * @param secondaryAction the secondary (second) acton of this sheet usually is a [JaArAction.Clickable.Destructive]
 * @param tertiaryAction the third (addition) action of this sheet default value is [JaArAction.Clickable.Hidden]
 * @param animationEasing easing of tween animation on dismiss and on show default [LinearEasing]
 * @param animationDuration duration of animation in millis default 400 millisecond
 * @param onDismissRequest this function is applied when clicking scrim (on start animation) or when apply any
 * action of buttons when it have [JaArAction.Clickable.dismissOnAction] true, set value of [visible]
 * to false inside this callback function,
 * @param onBackButtonClicked optional, nullable, if not null then header will have icon button similar
 * to navigateUpButton which do similar action, use it when side sheet have more than one content in it
 * (like tabs) or nested content otherwise let it null, and the icon button will be invisible,
 * @param onEndCollapseAnimation when animation ended and side sheet should be invisible
 * @param content content of side sheet, with weight(1f) in the column scope (with header and
 * actions that takes wrap content height) default padding already provided
 * @see [JaArAction.Clickable]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalSideSheet(
    modifier: Modifier = Modifier,
    visible: Boolean,
    title: JaArDynamicString?,
    animationEasing: Easing = FastOutSlowInEasing,
    onDismissRequest: () -> Unit,
    onBackButtonClicked: (() -> Unit)? = null,
    onEndCollapseAnimation: () -> Unit,
    primaryAction: (@Composable RowScope.() -> Unit)?,
    secondaryAction: (@Composable RowScope.() -> Unit)?,
    tertiaryAction: (@Composable () -> Unit)?,
    content: @Composable () -> Unit,
) {
    ModalSideSheetPopup(
        onDismissRequest = onDismissRequest,
        windowInsets = BottomSheetDefaults.windowInsets,
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val cornerSize = 16.dp
            val cornerSizePixel = LocalDensity.current.density * cornerSize.value
            val sheetMaxWidth = cornerSize + dimensionResource(id = R.dimen.side_sheet_max_width)
            val sheetMaxWidthPixel = LocalDensity.current.density * sheetMaxWidth.value
            val offsetAnimatable by remember {
                mutableStateOf(
                    Animatable(
                        sheetMaxWidthPixel
                    )
                )
            }
            LaunchedEffect(key1 = visible) {
                offsetAnimatable.animateTo(
                    targetValue = if (visible) cornerSizePixel else sheetMaxWidthPixel,
                    animationSpec = tween(
                        durationMillis = animFilterSheet,
                        easing = animationEasing
                    )
                )
                if (!visible) {
                    onEndCollapseAnimation()
                }
            }

            Scrim(
                color = BottomSheetDefaults.ScrimColor,
                onDismissRequest = onDismissRequest,
                visible = visible
            )
            val bottomSheetPaneTitle = "pane title"
            Surface(
                modifier = Modifier
                    .width(sheetMaxWidth)
                    .fillMaxSize()
                    .align(Alignment.TopEnd)
                    .semantics { paneTitle = bottomSheetPaneTitle }
                    .offset {
                        IntOffset(
                            offsetAnimatable.value.roundToInt(),
                            0,
                        )
                    },
                shape = RoundedCornerShape(CornerSize(16.dp)),
                color = BottomSheetDefaults.ContainerColor,
//                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                tonalElevation = BottomSheetDefaults.Elevation,
            ) {
                ModalSideSheetHeaderContentActions(
                    modifier = modifier,
                    title = title,
                    primaryAction = primaryAction,
                    secondaryAction = secondaryAction,
                    tertiaryAction = tertiaryAction,
                    onBackButtonClicked = onBackButtonClicked,
                    onDismiss = onDismissRequest,
                    content = content,
                )
            }
        }
    }
}

@Composable
private fun ModalSideSheetHeaderContentActions(
    modifier: Modifier = Modifier,
    title: JaArDynamicString?,
    onBackButtonClicked: (() -> Unit)?,
    onDismiss: () -> Unit,
    primaryAction: @Composable() (RowScope.() -> Unit)?,
    secondaryAction: @Composable() (RowScope.() -> Unit)?,
    tertiaryAction: @Composable() (() -> Unit)?,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            // padding according m3 guidelines
            // horizontal padding will be applied to content according to existence of divider
            .padding(vertical = 24.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ModalSideSheetHeader(
            title = title,
            onBackButtonClicked = onBackButtonClicked,
            onDismiss = onDismiss
        )
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 24.dp)
                .weight(1f)
        )
        { content() }
        ModalSideSheetActions(
            primaryAction = primaryAction,
            secondaryAction = secondaryAction,
            tertiaryAction = tertiaryAction,
        )
    }
}

@Composable
private fun ModalSideSheetHeader(
    modifier: Modifier = Modifier,
    title: JaArDynamicString?,
    onBackButtonClicked: (() -> Unit)?,
    onDismiss: () -> Unit,
) {
    Row(
        // padding according m3 guidelines, vertical padding already applied to parent
        modifier = modifier.padding(start = 16.dp, end = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBackButtonClicked != null) {
            IconButton(onClick = onBackButtonClicked) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
        }
        Text(
            modifier = Modifier.weight(1f),
            text = title?.value ?: "",
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, null)
        }
    }
}

@Composable
private fun ModalSideSheetActions(
    modifier: Modifier = Modifier,
    primaryAction: @Composable() (RowScope.() -> Unit)?,
    secondaryAction: @Composable() (RowScope.() -> Unit)?,
    tertiaryAction: @Composable() (() -> Unit)?,
) {
    Column(
        modifier = modifier,
//         .height(72.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = DividerDefaults.Thickness
        )
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (primaryAction != null) {
                primaryAction()
            }
            if (secondaryAction != null) {
                secondaryAction()
            }
            Spacer(Modifier.weight(1f))
            if (tertiaryAction != null) {
                tertiaryAction()
            }
        }
    }
}

// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
// DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE - DON'T TOUCHE
@Composable
private fun Scrim(
    color: Color,
    onDismissRequest: () -> Unit,
    visible: Boolean
) {
    if (color.isSpecified) {
        val alpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = TweenSpec(), label = ""
        )
        val dismissSheet = if (visible) {
            Modifier
                .pointerInput(onDismissRequest) {
                    detectTapGestures {
                        onDismissRequest()
                    }
                }
                .clearAndSetSemantics {}
        } else {
            Modifier
        }
        Canvas(
            Modifier
                .fillMaxSize()
                .then(dismissSheet)
        ) {
            drawRect(color = color, alpha = alpha)
        }
    }
}

@Composable
private fun ModalSideSheetPopup(
    onDismissRequest: () -> Unit,
    windowInsets: WindowInsets,
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    val id = rememberSaveable { UUID.randomUUID() }
    val parentComposition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    val modalSideSheetWindow = remember {
        ModalSideSheetWindow(
            onDismissRequest = onDismissRequest,
            composeView = view,
            saveId = id
        ).apply {
            setCustomContent(
                parent = parentComposition,
                content = {
                    Box(
                        Modifier
                            .semantics { this.popup() }
                            .windowInsetsPadding(windowInsets)
                            .imePadding()
                    ) {
                        currentContent()
                    }
                }
            )
        }
    }

    DisposableEffect(modalSideSheetWindow) {
        modalSideSheetWindow.show()
        onDispose {
            modalSideSheetWindow.disposeComposition()
            modalSideSheetWindow.dismiss()
        }
    }
}

private class ModalSideSheetWindow(
    private var onDismissRequest: () -> Unit,
    private val composeView: View,
    saveId: UUID,
) :
    AbstractComposeView(composeView.context),
    ViewTreeObserver.OnGlobalLayoutListener,
    ViewRootForInspector {
    init {
        id = android.R.id.content
        // Set up view owners
        setViewTreeLifecycleOwner(composeView.findViewTreeLifecycleOwner())
        setViewTreeViewModelStoreOwner(composeView.findViewTreeViewModelStoreOwner())
        setViewTreeSavedStateRegistryOwner(composeView.findViewTreeSavedStateRegistryOwner())
        setTag(androidx.compose.ui.R.id.compose_view_saveable_id_tag, "Popup:$saveId")
        // Enable children to draw their shadow by not clipping them
        clipChildren = false
    }

    private val windowManager =
        composeView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val displayWidth: Int
        get() {
            val density = context.resources.displayMetrics.density
            return (context.resources.configuration.screenWidthDp * density).roundToInt()
        }

    private val params: WindowManager.LayoutParams =
        WindowManager.LayoutParams().apply {
            // Position bottom sheet from the bottom of the screen
            gravity = Gravity.BOTTOM or Gravity.START
            // Application panel window
            type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            // Fill up the entire app view
            width = displayWidth
            height = WindowManager.LayoutParams.MATCH_PARENT

            // Format of screen pixels
            format = PixelFormat.TRANSLUCENT
            // Title used as fallback for a11y services
            title = composeView.context.resources.getString(
                androidx.compose.ui.R.string.default_popup_window_title
            )
            // Get the Window token from the parent view
            token = composeView.applicationWindowToken

            // Flags specific to modal bottom sheet.
            flags = flags and (
                    WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES or
                            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                    ).inv()

            flags = flags or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        }

    private var content: @Composable () -> Unit by mutableStateOf({})

    override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    @Composable
    override fun Content() {
        content()
    }

    fun setCustomContent(
        parent: CompositionContext? = null,
        content: @Composable () -> Unit
    ) {
        parent?.let { setParentCompositionContext(it) }
        this.content = content
        shouldCreateCompositionOnAttachedToWindow = true
    }

    fun show() {
        windowManager.addView(this, params)
    }

    fun dismiss() {
        setViewTreeLifecycleOwner(null)
        setViewTreeSavedStateRegistryOwner(null)
        composeView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        windowManager.removeViewImmediate(this)
    }

    /**
     * Taken from PopupWindow. Calls [onDismissRequest] when back button is pressed.
     */
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyDispatcherState == null) {
                return super.dispatchKeyEvent(event)
            }
            if (event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
                val state = keyDispatcherState
                state?.startTracking(event, this)
                return true
            } else if (event.action == KeyEvent.ACTION_UP) {
                val state = keyDispatcherState
                if (state != null && state.isTracking(event) && !event.isCanceled) {
                    onDismissRequest()
                    return true
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onGlobalLayout() {
        // No-op
    }
}
