package com.brokenchess.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.brokenchess.UiUtils
import com.brokenchess.gameEngine.MoveSelector
import kotlin.math.floor

@Composable
fun BoardView(boardState: Array<Array<String>>, moveSelector: MoveSelector) {
    val boardSize = 8

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        //snap to a whole number of pixels per square so the highlight grid aligns exactly with the drawn board
        val boardSizeDp = with(LocalDensity.current) {
            val squarePx = floor(UiUtils.calculateBoardSize(maxWidth.value, maxHeight.value).dp.toPx() / boardSize)
            (squarePx * boardSize).toDp()
        }

        Box(modifier = Modifier.size(boardSizeDp)) {
            UiUtils.ChessBoard(modifier = Modifier.fillMaxSize())

            val selectedSquare = moveSelector.getLastClickedSquare()

            Column(modifier = Modifier.fillMaxSize()) {
                for (row in 0 until boardSize) {
                    Row(modifier = Modifier.weight(1f)) {
                        for (column in 0 until boardSize) {
                            val isSelected = selectedSquare != null &&
                                    selectedSquare[0] == row &&
                                    selectedSquare[1] == column
                            SquareHighlight(
                                piece = boardState[row][column],
                                isSelected = isSelected,
                                onClick = { moveSelector.notifyClick(row, column) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
