package com.brokenchess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ChessboardResizeTest {

    @Test
    void boardScaleRemainsProportionalAsWindowSizeChanges() {
        assertEquals(164.0f, UiUtils.calculateBoardSize(200f, 200f), 0.001f);
        assertEquals(164.0f, UiUtils.calculateBoardSize(400f, 200f), 0.001f);
        assertEquals(164.0f, UiUtils.calculateBoardSize(200f, 400f), 0.001f);
    }
}
