package com.example.lightsout

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LightsOutGameTest {
    @Test fun centerMoveTogglesCenterAndFourNeighbours() {
        val result = LightsOutGame.toggle(List(9) { false }, 4)
        assertEquals(listOf(false, true, false, true, true, true, false, true, false), result)
    }

    @Test fun cornerMoveTogglesThreeCells() {
        val result = LightsOutGame.toggle(List(9) { false }, 0)
        assertEquals(listOf(true, true, false, true, false, false, false, false, false), result)
    }

    @Test fun togglingSameCellTwiceRestoresBoard() {
        val board = List(9) { it % 2 == 0 }
        assertEquals(board, LightsOutGame.toggle(LightsOutGame.toggle(board, 7), 7))
    }

    @Test fun emptyBoardIsSolved() {
        assertTrue(LightsOutGame.isSolved(List(9) { false }))
    }

    @Test fun generatedPuzzleHasNineCells() {
        assertEquals(9, LightsOutGame.newPuzzle().size)
    }
}
