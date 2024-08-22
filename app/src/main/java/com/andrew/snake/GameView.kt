package com.andrew.snake

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var snakeBody: List<Position>? = null
    var apple: Position? = null
    private var size: Int = 0
    private var gap: Int = 3
    private val paint = Paint().apply { color = Color.GREEN }
    private val paintApple = Paint().apply { color = Color.RED }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.run {
            apple?.run {
                drawRect(
                    (x * size).toFloat() + gap,
                    (y * size).toFloat() + gap,
                    ((x + 1) * size).toFloat() - gap,
                    ((y + 1) * size).toFloat() - gap,
                    paintApple
                )
            }
            snakeBody?.forEach {
                drawRect(
                    (it.x * size).toFloat() + gap,
                    (it.y * size).toFloat() + gap,
                    ((it.x + 1) * size).toFloat() - gap,
                    ((it.y + 1) * size).toFloat() - gap,
                    paint
                )
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        size = width / 20
    }
}