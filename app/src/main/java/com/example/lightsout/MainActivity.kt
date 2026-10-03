package com.example.lightsout

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize()) { LightsOutScreen() }
            }
        }
    }
}

private const val PREFS_NAME = "lights_out"
private const val KEY_START = "start_board"
private const val KEY_BOARD = "board"
private const val KEY_MOVES = "moves"

private fun encode(board: List<Boolean>): String = board.joinToString("") { if (it) "1" else "0" }

private fun decode(value: String?): List<Boolean>? =
    value?.takeIf { it.length == LightsOutGame.CELL_COUNT }?.map { it == '1' }

@Composable
private fun LightsOutScreen() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    val startingBoard = remember {
        mutableStateListOf<Boolean>().apply {
            addAll(decode(prefs.getString(KEY_START, null)) ?: LightsOutGame.newPuzzle())
        }
    }
    val board = remember {
        mutableStateListOf<Boolean>().apply {
            addAll(decode(prefs.getString(KEY_BOARD, null)) ?: startingBoard)
        }
    }
    var moves by remember { mutableIntStateOf(prefs.getInt(KEY_MOVES, 0)) }
    var solved by remember { mutableStateOf(LightsOutGame.isSolved(board)) }

    LaunchedEffect(Unit) {
        prefs.edit()
            .putString(KEY_START, encode(startingBoard))
            .putString(KEY_BOARD, encode(board))
            .putInt(KEY_MOVES, moves)
            .apply()
    }

    fun save() {
        prefs.edit()
            .putString(KEY_START, encode(startingBoard))
            .putString(KEY_BOARD, encode(board))
            .putInt(KEY_MOVES, moves)
            .apply()
    }

    fun newPuzzle() {
        val puzzle = LightsOutGame.newPuzzle()
        board.clear(); board.addAll(puzzle)
        moves = 0
        solved = false
        startingBoard.clear(); startingBoard.addAll(puzzle)
        save()
    }

    fun reset() {
        board.clear(); board.addAll(startingBoard)
        moves = 0
        solved = LightsOutGame.isSolved(board)
        save()
    }

    fun pressCell(index: Int) {
        val next = LightsOutGame.toggle(board, index)
        board.clear(); board.addAll(next)
        moves++
        solved = LightsOutGame.isSolved(board)
        save()
    }

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("LIGHTS OUT", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(
            if (solved) "Solved! 🎉" else "Turn every light off",
            fontSize = 18.sp,
            color = if (solved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(24.dp))
        Text("Moves: $moves", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(24.dp))

        Column(Modifier.size(300.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) { row ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(3) { col ->
                        val index = row * 3 + col
                        LightCell(board[index], { pressCell(index) }, Modifier.weight(1f))
                    }
                }
            }
        }

        Spacer(Modifier.height(28.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = ::newPuzzle) { Text("New Puzzle") }
            Button(
                onClick = ::reset,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) { Text("Reset") }
        }
    }
}

@Composable
private fun LightCell(on: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val background = if (on) Color(0xFFFFC107) else Color(0xFF424242)
    Button(
        onClick = onClick,
        modifier = modifier.height(95.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = if (on) Color.Black else Color.White
        )
    ) {
        Text(if (on) "ON" else "OFF", fontWeight = FontWeight.Bold)
    }
}
