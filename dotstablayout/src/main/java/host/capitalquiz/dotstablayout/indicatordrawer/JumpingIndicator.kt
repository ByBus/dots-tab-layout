package host.capitalquiz.dotstablayout.indicatordrawer

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.view.View
import host.capitalquiz.dotstablayout.Dot
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState
import kotlin.math.abs
import kotlin.math.sign

internal abstract class JumpingIndicator(params: DotHolder) : BaseIndicator(params) {
    private var previousCurrent: Dot? = null
    private var previousNext: Dot? = null
    private var updateSign = true
    private var sign = 0f
    private var directionChanged = true

    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        super.draw(canvas, paint, dragState)
        val current = params.currentDot()
        val next = params.nextDot()
        val currentPosition = current.left + current.width / 2
        val nextPosition = next.left + next.width / 2
        val distance = abs(currentPosition - nextPosition)
        val radius = distance / 2
        val dotsBetween = abs(current.index - next.index) - 1

        dragState.indicatorPosition { _, y, distanceFraction ->
            val finished = isFinished(distanceFraction)
            val originalDotVisibility = if (finished) View.VISIBLE else View.INVISIBLE
            previousCurrent?.visibility = originalDotVisibility
            previousNext?.visibility = originalDotVisibility

            if (sign != 0f && sign != distanceFraction.sign) {
                directionChanged = true
                updateSign = true
            }

            if (updateSign) {
                sign = distanceFraction.sign
                updateSign = false
            }

            val fraction = abs(distanceFraction)
            val offset = calculateOffsetFromCenter(distance, radius, fraction, dotsBetween)

            val xPosition = currentPosition + sign * radius
            val yOffset = sign * offset.y
            val xOffset = sign * offset.x
            val currentX = xPosition + xOffset
            var currentY = y - yOffset
            val nextX = xPosition - xOffset
            var nextY = y + yOffset

            if (directionChanged) currentY = nextY.also { nextY = currentY }

            paint.drawWithStroke(params.dotStrokeWidth()) {
                canvas.drawCircle(nextX, nextY, dotRadius(current, fraction), paint)
            }

            canvas.drawCircle(
                currentX,
                currentY,
                indicatorRadius(current, 1f - fraction),
                paint
            )

            previousCurrent = current.takeUnless { finished }
            previousNext = next.takeUnless { finished }

            if (finished) {
                updateSign = true
                directionChanged = false
            }
        }
    }

    protected open fun indicatorRadius(current: Dot, fraction: Float): Float =
        current.radius + params.dotStrokeWidth() / 2

    protected open fun dotRadius(current: Dot, fraction: Float): Float = current.radius


    protected abstract fun calculateOffsetFromCenter(
        horizontalLength: Int,
        radius: Int,
        fraction: Float,
        dotsBetween: Int,
    ): PointF

    private fun isFinished(fraction: Float): Boolean = fraction == 0f
}

private fun Paint.drawWithStroke(width: Float, block: Paint.() -> Unit) {
    val tempStyle = style
    val tempStrokeWidth = strokeWidth
    style = Paint.Style.STROKE
    strokeWidth = width
    block.invoke(this)
    style = tempStyle
    strokeWidth = tempStrokeWidth
}
