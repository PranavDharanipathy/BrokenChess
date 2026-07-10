@file:JvmName("ApplicationKt")

package com.brokenchess

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Rectangle
import com.brokenchess.gameEngine.ChessEngine
import com.brokenchess.gameEngine.MoveSelector
import com.brokenchess.ui.BoardView
import com.brokenchess.ui.TitleBar
import kotlinx.coroutines.delay

fun startApp() {
    
    val engine = ChessEngine()

    application {
        val windowState = rememberWindowState()

        Window(
            onCloseRequest = ::exitApplication,
            title = "",
            state = windowState,
            undecorated = true
        ) {
            var refreshTick by remember { mutableStateOf(0) }
            //Borderless fullscreen covering the monitor with a normal window instead of WindowPlacement.Fullscreen.
            var isFullscreen by remember { mutableStateOf(false) }
            var savedBounds by remember { mutableStateOf<Rectangle?>(null) }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(10)
                    engine.update()
                    refreshTick++
                }
            }

            MaterialTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    TitleBar(
                        isFullscreen = isFullscreen,
                        onMinimize = { windowState.isMinimized = true },
                        onToggleFullscreen = {
                            if (isFullscreen) {
                                savedBounds?.let { window.bounds = it }
                                isFullscreen = false
                            } else {
                                savedBounds = window.bounds
                                window.bounds = window.graphicsConfiguration.bounds
                                isFullscreen = true
                            }
                        },
                        onClose = ::exitApplication
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        ChessScreen(engine.getBoardState(), engine.getMoveSelector(), refreshTick)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ChessScreen(boardState: Array<Array<String>>, moveSelector: MoveSelector, refreshTick: Int) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(24.dp, 16.dp, 24.dp, 16.dp),
        contentAlignment = Alignment.Center
    ) {
        BoardView(boardState, moveSelector)
    }
}