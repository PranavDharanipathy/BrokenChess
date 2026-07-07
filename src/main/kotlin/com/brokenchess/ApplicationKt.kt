@file:JvmName("ApplicationKt")

package com.brokenchess

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.brokenchess.engine.ChessEngine
import kotlinx.coroutines.delay

fun startApp() {
    val engine = ChessEngine()

    application {
        Window(onCloseRequest = ::exitApplication, title = "BrokenChess") {
            var refreshTick by remember { mutableStateOf(0) }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(100)
                    engine.update()
                    refreshTick++
                }
            }

            MaterialTheme {
                ChessScreen(engine.getBoardState(), refreshTick)
            }
        }
    }
}

@Composable
@Preview
fun ChessScreen(boardState: Array<String>, refreshTick: Int) {
    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp, 16.dp, 24.dp, 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val titleBitmap = UiUtils.loadImageBitmap("assets/docs/title.svg")
            if (titleBitmap != null) {
                Image(
                    painter = BitmapPainter(titleBitmap),
                    contentDescription = "BrokenChess title",
                    modifier = Modifier.fillMaxWidth(0.95f).heightIn(min = 60.dp, max = 120.dp).padding(bottom = 12.dp),
                    contentScale = ContentScale.Fit
                )
            } else {
                Text("BrokenChess")
            }
            Spacer(modifier = Modifier.height(8.dp))
            BoardView(boardState)
        }
    }
}

@Composable
private fun BoardView(boardState: Array<String>) {
    val boardSize = 8
    val boardPainter = UiUtils.loadImageBitmap("assets/game/ChessBoard.jpg")?.let { BitmapPainter(it) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        val boardSizeDp = UiUtils.calculateBoardSize(maxWidth.value, maxHeight.value).dp

        Box(modifier = Modifier.size(boardSizeDp)) {
            if (boardPainter != null) {
                Image(
                    painter = boardPainter,
                    contentDescription = "Chess board",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            Column(modifier = Modifier.fillMaxSize()) {
                for (row in 0 until boardSize) {
                    Row(modifier = Modifier.weight(1f)) {
                        for (column in 0 until boardSize) {
                            val index = row * boardSize + column
                            val piece = boardState[index]
                            val assetPath = when (piece) {
                                "wp" -> "assets/game/pieces/WhitePawn.png"
                                "bp" -> "assets/game/pieces/BlackPawn.png"
                                "wr" -> "assets/game/pieces/WhiteRook.png"
                                "br" -> "assets/game/pieces/BlackRook.png"
                                "wn" -> "assets/game/pieces/WhiteKnight.png"
                                "bn" -> "assets/game/pieces/BlackKnight.png"
                                "wb" -> "assets/game/pieces/WhiteBishop.png"
                                "bb" -> "assets/game/pieces/BlackBishop.png"
                                "wq" -> "assets/game/pieces/WhiteQueen.png"
                                "bq" -> "assets/game/pieces/BlackQueen.png"
                                "wk" -> "assets/game/pieces/WhiteKing.png"
                                "bk" -> "assets/game/pieces/BlackKing.png"
                                else -> null
                            }
                            val pieceBitmap = assetPath?.let { UiUtils.loadImageBitmap(it) }
                            Box(
                                modifier = Modifier.weight(1f).aspectRatio(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                if (pieceBitmap != null) {
                                    Image(
                                        painter = BitmapPainter(pieceBitmap),
                                        contentDescription = piece,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
