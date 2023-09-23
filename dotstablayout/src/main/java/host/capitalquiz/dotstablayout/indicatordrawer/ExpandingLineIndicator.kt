package host.capitalquiz.dotstablayout.indicatordrawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import host.capitalquiz.dotstablayout.Dot
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState
import kotlin.math.abs

internal class ExpandingLineIndicator(params: DotHolder) : BaseIndicator(params) {
    private var previousCurrent: Dot? = null
    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        super.draw(canvas, paint, dragState)
        dragState.indicatorPosition { _, _, distanceFraction ->
            val fraction = abs(distanceFraction)
            val next = params.nextDot()
            val current = params.currentDot()
            if (fraction != 0f){
                previousCurrent = next
                val nextSize = params.dotSize() * fraction
                next.radius = nextSize
                if (current != next) {
                    current.radius = params.dotSize() - nextSize
                }
            } else {
                current.radius = params.dotSize().toFloat()
                if (current != previousCurrent) {
                    previousCurrent?.radius = 0f
                }
            }
            next.requestLayout()
        }
    }

    override fun createDot(context: Context, position: Int): Dot {
        return ExpandingLineDot(context).apply {
            with(params) {
                layoutParams = dotLayoutParams().apply {
                    height = dotStrokeWidth().toInt()
                    width = (width - dotSize() + dotStrokeWidth()).toInt()
                }
                color = dotColor()
                strokeWidth = dotStrokeWidth()
                setStyle(Paint.Style.STROKE)
                index = position
            }
        }
    }

    private class ExpandingLineDot(context: Context) : Dot(context) {
        private var initWidth = 0
        init {
            paint.strokeCap = Paint.Cap.ROUND
        }
        override var radius: Float = 0f
            set(value) {
                field = value
                updateWidth()
            }

        private fun updateWidth() {
            if (initWidth == 0) initWidth = width
            this.layoutParams.width = initWidth + radius.toInt()
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val y = height / 2f
            val center = width / 2
            val half = radius / 2
            canvas.drawLine(center - half, y, center + half, y, paint)
        }
    }
}