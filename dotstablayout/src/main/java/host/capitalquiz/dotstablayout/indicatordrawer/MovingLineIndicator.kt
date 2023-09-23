package host.capitalquiz.dotstablayout.indicatordrawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.ColorUtils
import host.capitalquiz.dotstablayout.Dot
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState

internal class MovingLineIndicator(params: DotHolder) : BaseIndicator(params) {
    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        super.draw(canvas, paint, dragState)
        dragState.indicatorPosition { x, y, _ ->
            val halfWidth = params.dotSize() / 2
            canvas.drawLine(x - halfWidth, y, x + halfWidth, y, paint)
        }
    }

    override fun singleTimePaintUpdate(paint: Paint) {
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = params.dotStrokeWidth()
    }

    override fun createDot(context: Context, position: Int): Dot {
        return LineDot(context).apply {
            with(params) {
                layoutParams = dotLayoutParams().apply {
                    height = dotStrokeWidth().toInt()
                }
                color = ColorUtils.setAlphaComponent(dotColor(), 76)
                strokeWidth = dotStrokeWidth()
                radius = dotSize().toFloat()
                setStyle(Paint.Style.STROKE)
                index = position
            }
        }
    }

    private class LineDot(context: Context) : Dot(context) {

        init {
            paint.strokeCap = Paint.Cap.ROUND
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val y = height / 2f
            val center = width / 2
            val x1 = center - radius / 2
            val x2 = x1 + radius
            canvas.drawLine(x1, y, x2, y, paint)
        }
    }
}