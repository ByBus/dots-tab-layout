package host.capitalquiz.dotstablayout.indicatordrawer

import android.graphics.Canvas
import android.graphics.Paint
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState
import kotlin.math.abs

internal class ContractingDotIndicator(params: DotHolder) : BaseIndicator(params) {
    private val radius = params.dotSize() / 2 - params.dotStrokeWidth() + 1f
    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        super.draw(canvas, paint, dragState)
        dragState.indicatorPosition { _, y, distanceFraction ->
            val current = params.currentDot()
            val next = params.nextDot()
            val currentCenter = current.left + current.width / 2f
            val fraction = abs(distanceFraction)
            if (fraction != 0f) {
                canvas.drawCircle(currentCenter, y, radius * (1f - fraction), paint)
                if (current != next) {
                    val nextCenter = next.left + next.width / 2f
                    canvas.drawCircle(nextCenter, y, radius * fraction, paint)
                }
            } else {
                canvas.drawCircle(currentCenter, y, radius, paint)
            }
        }
    }

    override fun durationPerDot(dotPosition: Int): Long = 300L
}