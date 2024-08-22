package com.andrew.snake

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer

class SnakeViewModel : ViewModel() {
    val body = MutableLiveData<List<Position>>()
    val apple = MutableLiveData<Position>()
    val score = MutableLiveData<Int>()
    val gameState = MutableLiveData<GameState>()
    private val snakeBody = mutableListOf<Position>()
    private lateinit var applePos: Position
    private var direction = Direction.LEFT
    private var point: Int = 0

    fun start() {
        score.postValue(point)
        snakeBody
            .apply {
                add(Position(10, 10))
                add(Position(11, 10))
                add(Position(12, 10))
                add(Position(13, 10))
            }.also {
                body.value = it
            }
        generateApple()
        fixedRateTimer("timer", true, 500, 200) {
            val pos = snakeBody.first().copy().apply {
                when (direction) {
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                    Direction.UP -> y--
                    Direction.DOWN -> y++
                }
                if (snakeBody.contains(this) || x < 0 || x >= 20 || y < 0 || y >= 20) {
                    cancel()
                    gameState.postValue(GameState.GAME_OVER)
                }
            }

            snakeBody.add(0, pos)
            if (pos != applePos) {
                snakeBody.removeLast()
            } else {
                point += 100
                score.postValue(point)
                generateApple()
            }

            body.postValue(snakeBody)
        }
    }


    fun generateApple() {
        val spots = mutableListOf<Position>().apply {
            for (i in 0..19) {
                for (j in 0..19) {
                    add(Position(i, j))
                }
            }
        }
        spots.removeAll(snakeBody)
        spots.shuffle()
        applePos = spots.first()

        apple.postValue(applePos)
    }

    fun reset() {
        point=0
        snakeBody.clear()
        start()
    }
    fun move(dir: Direction) {
        direction = dir
    }
}

data class Position(var x: Int, var y: Int)
enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

enum class GameState {
    ONGOING, GAME_OVER
}