package com.brokenchess.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.brokenchess.UiUtils

@Composable
fun SquareHighlight(
    piece: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
        modifier = modifier
            .fillMaxSize()
            .clickable { onClick() },
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
        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Red.copy(alpha = 0.6f))
            )
        }
    }
}
