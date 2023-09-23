package host.capitalquiz.dotstablayout.indicatordrawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import host.capitalquiz.dotstablayout.Dot
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState
import kotlin.math.abs

internal class FillingDotIndicator(params: DotHolder) : BaseIndicator(params) {

    private val maxRadius = (params.dotSize() - params.dotStrokeWidth()) / 2
    private val totalRadius = params.dotSize() / 2f
    private val minRadius = totalRadius / 2
    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        dragState.indicatorPosition { x, y, distanceFraction ->
            val current = params.currentDot()
            val next = params.nextDot()
            val fraction = abs(distanceFraction)

            if (distanceFraction != 0f) {
                val nextDotRadius = minRadius + (maxRadius - minRadius) * fraction
                val currentDotRadius = minRadius + (maxRadius - nextDotRadius)
                val nextDotStrokeWidth = (totalRadius - nextDotRadius) * 2
                val currentDotStrokeWidth = (totalRadius - currentDotRadius) * 2
                next.strokeWidth = currentDotStrokeWidth
                next.radius = currentDotRadius
                if (current != next) {
                    current.strokeWidth = nextDotStrokeWidth
                    current.radius = nextDotRadius
                    current.invalidate()
                }
                next.invalidate()
            } else {
                current.strokeWidth = totalRadius
                current.radius = minRadius
                current.invalidate()
            }
        }
    }

    override fun durationPerDot(dotPosition: Int): Long = 300L

    override fun createDot(context: Context, position: Int): Dot =
        super.createDot(context, position).apply { radius = maxRadius }
}