package com.brokenchess

object BoardLayout {
    private const val BOARD_SCALE = 0.82f

    @JvmStatic
    fun calculateBoardSize(maxWidth: Float, maxHeight: Float): Float {
        val availableSpace = minOf(maxWidth, maxHeight)
        return availableSpace * BOARD_SCALE
    }
}
