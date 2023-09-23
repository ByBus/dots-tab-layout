package host.capitalquiz.dotstablayout.indicatordrawer

import android.graphics.Canvas
import android.graphics.Paint
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState
import kotlin.math.abs
import kotlin.math.pow

internal class CrawlingDotIndicator(
    params: DotHolder,
    private val sizeInterpolator: Interpolator = SizeInterpolator(),
) : BaseIndicator(params) {
    private val accelerateInterpolator = AccelerateInterpolator()
    private val decelerateInterpolator = DecelerateInterpolator()
    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        super.draw(canvas, paint, dragState)
        paint.alpha = 255
        val current = params.currentDot()
        val next = params.nextDot()

        dragState.indicatorPosition { _, y, distanceFraction ->
            val distanceFractionAbs = abs(distanceFraction)
            val betweenDots = next.left - current.left
            val currentPosition = current.left + current.width / 2

            val xPosition1 =
                currentPosition + betweenDots * accelerateInterpolator.getInterpolation(
                    distanceFractionAbs
                )
            val xPosition2 =
                currentPosition + betweenDots * decelerateInterpolator.getInterpolation(
                    distanceFractionAbs
                )
            paint.strokeWidth =
                params.dotSize() * sizeInterpolator.getInterpolation(distanceFractionAbs)
            canvas.drawLine(xPosition1, y, xPosition2, y, paint)
        }
    }

    override fun singleTimePaintUpdate(paint: Paint) {
        paint.strokeCap = Paint.Cap.ROUND
    }
}

private class SizeInterpolator : Interpolator {
    override fun getInterpolation(input: Float): Float {
        val x = abs(input)
        return 1.1f * (x - 0.5f).pow(2) + 0.72f
    }
}

