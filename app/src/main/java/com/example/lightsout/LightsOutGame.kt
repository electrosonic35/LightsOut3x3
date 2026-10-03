package com.example.lightsout

/** Pure game logic for a 3x3 Lights Out board. */
object LightsOutGame {
    const val SIZE = 3
    const val CELL_COUNT = SIZE * SIZE

    /** Toggles the selected cell plus its orthogonal neighbours. */
    fun toggle(board: List<Boolean>, index: Int): List<Boolean> {
        require(board.size == CELL_COUNT) { "Board must contain 9 cells" }
        require(index in 0 until CELL_COUNT) { "Cell index out of range" }

        val result = board.toMutableList()
        val row = index / SIZE
        val col = index % SIZE

        fun flip(r: Int, c: Int) {
            if (r in 0 until SIZE && c in 0 until SIZE) {
                val i = r * SIZE + c
                result[i] = !result[i]
            }
        }

        flip(row, col)
        flip(row - 1, col)
        flip(row + 1, col)
        flip(row, col - 1)
        flip(row, col + 1)
        return result
    }

    fun isSolved(board: List<Boolean>): Boolean = board.size == CELL_COUNT && board.none { it }

    /** Builds a guaranteed-solvable puzzle by applying random legal moves to a solved board. */
    fun newPuzzle(): List<Boolean> {
        var board = List(CELL_COUNT) { false }
        repeat((6..20).random()) {
            board = toggle(board, (0 until CELL_COUNT).random())
        }
        return board
    }
}
