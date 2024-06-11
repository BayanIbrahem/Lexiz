package com.dev.bayan.ibrahim.core.ui.components.action

/**
 * just represent each action of clickable, checkable, and selectable actions
 */
enum class JaArAction {;

    enum class Clickable {
        BUTTON,
        OUTLINED_BUTTON,
        TEXT_BUTTON,
        ICON_BUTTON,
    }

    enum class Checkable {
        CHECK_BOX,
        RADIO_BUTTON,
        SWITCH,
    }
}