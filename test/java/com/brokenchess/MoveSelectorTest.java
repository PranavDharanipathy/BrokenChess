package com.brokenchess.gameEngine;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class MoveSelectorTest {

    @Test
    void recordsTheLastClickedSquareAndExposesItAfterUpdate() {
        MoveSelector selector = new MoveSelector();

        assertNull(selector.getLastClickedSquare());

        selector.notifyClick(3, 4);
        selector.update();

        assertArrayEquals(new Integer[] {3, 4}, selector.getLastClickedSquare());
    }
}
