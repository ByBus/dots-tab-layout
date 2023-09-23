package host.capitalquiz.dotstablayout.indicatordrawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import host.capitalquiz.dotstablayout.Dot
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState
import kotlin.math.abs

internal class GrowingCircleIndicator(params: DotHolder) : BaseIndicator(params) {
    private val maxRadius = (params.dotSize() - params.dotStrokeWidth()) / 2
    private val minRadius = (params.dotSize() / 2) - 3 * params.dotStrokeWidth()
    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        super.draw(canvas, paint, dragState)
        dragState.indicatorPosition { x, y, distanceFraction ->
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

    override fun singleTimePaintUpdate(paint: Paint) {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = params.dotStrokeWidth()
    }
    override fun createDot(context: Context, position: Int): Dot {
        return super.createDot(context, position).apply {
            radius = minRadius + params.dotStrokeWidth() / 2f
            setStyle(Paint.Style.FILL)
        }
    }
}