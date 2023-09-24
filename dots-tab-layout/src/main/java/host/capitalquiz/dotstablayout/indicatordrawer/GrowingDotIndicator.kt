package host.capitalquiz.dotstablayout.indicatordrawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import host.capitalquiz.dotstablayout.Dot
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState
import kotlin.math.abs

internal class GrowingDotIndicator(params: DotHolder) : BaseIndicator(params)  {
    private val maxRadius = params.dotSize() / 2f
    private val minRadius = params.dotSize() / 2 - params.dotStrokeWidth()

    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        super.draw(canvas, paint, dragState)
        dragState.indicatorPosition { _, y, distanceFraction ->
            val current = params.currentDot()
            val next = params.nextDot()
            val currentCenter = current.left + current.width / 2f
            val fraction = abs(distanceFraction)
            if (fraction != 0f) {
                val nextDotRadius = minRadius + (maxRadius - minRadius) * fraction
                val currentDotRadius = minRadius + (maxRadius - nextDotRadius)
                canvas.drawCircle(currentCenter, y, currentDotRadius, paint)
                if (current != next) {
                    val nextCenter = next.left + next.width / 2f
                    canvas.drawCircle(nextCenter, y, nextDotRadius, paint)
                }
            } else {
                canvas.drawCircle(currentCenter, y, maxRadius, paint)
            }
        }
    }

    override fun createDot(context: Context, position: Int): Dot {
        return super.createDot(context, position).apply {
            radius = minRadius
            setStyle(Paint.Style.FILL)
        }
    }

    override fun durationPerDot(dotPosition: Int): Long = 250L
}