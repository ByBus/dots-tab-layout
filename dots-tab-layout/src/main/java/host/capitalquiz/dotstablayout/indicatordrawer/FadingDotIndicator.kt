package host.capitalquiz.dotstablayout.indicatordrawer

import android.graphics.Canvas
import android.graphics.Paint
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState
import kotlin.math.abs
import kotlin.math.roundToInt

internal class FadingDotIndicator(params: DotHolder) : BaseIndicator(params) {
    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        super.draw(canvas, paint, dragState)
        dragState.indicatorPosition { _, y, distanceFraction ->
            val current = params.currentDot()
            val next = params.nextDot()
            val currentCenter = current.left + current.width / 2f
            val fraction = abs(distanceFraction)
            val radius = (params.dotSize() shr 1).toFloat()
            if (fraction != 0f) {
                paint.withAlpha(1f - fraction) {
                    canvas.drawCircle(currentCenter, y, radius, paint)
                }
                if (current != next) {
                    val nextCenter = next.left + next.width / 2f
                    paint.withAlpha(fraction) {
                        canvas.drawCircle(nextCenter, y, radius, paint)
                    }
                }
            } else {
                paint.alpha = 255
                canvas.drawCircle(currentCenter, y, radius, paint)
            }
        }
    }

    override fun durationPerDot(dotPosition: Int): Long = 300L
}

private fun Paint.withAlpha(fraction: Float, block: Paint.() -> Unit) {
    val temp = alpha
    alpha = (255 * fraction).roundToInt()
    block.invoke(this)
    alpha = temp
}