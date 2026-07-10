package com.brokenchess.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowScope
import java.awt.MouseInfo
import java.awt.Point

private val TITLE_BAR_COLOR = Color(0xFF262D57)
private val TITLE_BAR_COLOR_FULLSCREEN = Color.Black
private val BUTTON_HOVER_COLOR = Color.White.copy(alpha = 0.12f)
private val CLOSE_HOVER_COLOR = Color(0xFFE81123)

@Composable
fun WindowScope.TitleBar(
    isFullscreen: Boolean,
    onMinimize: () -> Unit,
    onToggleFullscreen: () -> Unit,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(if (isFullscreen) TITLE_BAR_COLOR_FULLSCREEN else TITLE_BAR_COLOR),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                
                //moving window by draggin title bar
                .pointerInput(Unit) {
                    var initialMousePos = Point()
                    var initialWindowPos = Point()
                    var isDragging = false

                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val change = event.changes.first()
                            val changedToPressed = !change.previousPressed && change.pressed

                            if (event.buttons.isPrimaryPressed && changedToPressed) {
                                initialMousePos = MouseInfo.getPointerInfo().location
                                initialWindowPos = Point(window.x, window.y)
                                isDragging = true
                            }

                            if (!event.buttons.isPrimaryPressed) {
                                isDragging = false
                            }

                            if (event.type == PointerEventType.Move && isDragging) {
                                val mousePos = MouseInfo.getPointerInfo().location
                                window.setLocation(
                                    initialWindowPos.x + (mousePos.x - initialMousePos.x),
                                    initialWindowPos.y + (mousePos.y - initialMousePos.y)
                                )
                            }
                        }
                    }
                }
        )
        TitleBarButton(label = "\u2212", onClick = onMinimize)
        TitleBarButton(label = if (isFullscreen) "\u25A3" else "\u25A1", onClick = onToggleFullscreen)
        TitleBarButton(label = "\u2715", onClick = onClose, isClose = true)
    }
}

@Composable
private fun TitleBarButton(
    label: String,
    onClick: () -> Unit,
    isClose: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor = when {
        isHovered && isClose -> CLOSE_HOVER_COLOR
        isHovered -> BUTTON_HOVER_COLOR
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(46.dp)
            .hoverable(interactionSource)
            .clickable { onClick() }
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
