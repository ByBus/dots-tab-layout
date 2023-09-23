package host.capitalquiz.dotstablayout.indicatordrawer

import android.graphics.Canvas
import android.graphics.Paint
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState

internal class DiscreteDotIndicator(params: DotHolder) : BaseIndicator(params) {
    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        super.draw(canvas, paint, dragState)
        dragState.indicatorPosition { _, y, distanceFraction ->
            val current = params.currentDot()
            val radius = current.radius - 2f - params.dotStrokeWidth()
            val xPosition = if (distanceFraction != 0f) {
                val next = params.nextDot()
                next.left + next.width / 2
            } else {
               current.left + current.width / 2
            }
            canvas.drawCircle(xPosition.toFloat(), y, radius, paint)
        }
    }

    override fun durationPerDot(dotPosition: Int): Long = 0L
}