package host.capitalquiz.dotstablayout.indicatordrawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import host.capitalquiz.dotstablayout.Dot
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DotsTabLayoutDrawer
import host.capitalquiz.dotstablayout.DragState

internal abstract class BaseIndicator(protected val params: DotHolder) : DotsTabLayoutDrawer {
    private var paintUpdated = false

    override fun durationPerDot(dotPosition: Int): Long = 250L + dotPosition * 50L

    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        if (paintUpdated.not()) {
            singleTimePaintUpdate(paint)
            paintUpdated = true
        }
    }

    protected open fun singleTimePaintUpdate(paint: Paint) = Unit

    override fun createDot(context: Context, position: Int): Dot {
        return CircleDot(context).apply {
            with(params) {
                layoutParams = dotLayoutParams()
                color = dotColor()
                strokeWidth = dotStrokeWidth()
                radius = (dotSize() - dotStrokeWidth()) / 2
                index = position
            }
        }
    }

    protected class CircleDot(context: Context) : Dot(context) {
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawCircle(width / 2f, height / 2f, radius, paint)
        }
    }
}