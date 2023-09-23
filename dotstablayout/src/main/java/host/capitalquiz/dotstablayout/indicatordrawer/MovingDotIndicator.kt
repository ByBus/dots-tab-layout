package host.capitalquiz.dotstablayout.indicatordrawer

import android.graphics.Canvas
import android.graphics.Paint
import android.view.animation.Interpolator
import host.capitalquiz.dotstablayout.DotHolder
import host.capitalquiz.dotstablayout.DragState
import kotlin.math.abs
import kotlin.math.pow

internal class MovingDotIndicator(
    params: DotHolder,
    private val radiusInterpolator: Interpolator = ParabolicInterpolator(),
) : BaseIndicator(params) {
    override fun draw(canvas: Canvas, paint: Paint, dragState: DragState) {
        dragState.indicatorPosition { x, y, distanceFraction ->
            val current = params.currentDot()
            val fraction = radiusInterpolator.getInterpolation(distanceFraction)
            val radius = (current.radius - 2f - params.dotStrokeWidth()) * fraction
            canvas.drawCircle(x, y, radius, paint)
        }
    }
}

private class ParabolicInterpolator : Interpolator {
    override fun getInterpolation(input: Float): Float {
        val x = abs(input)
        return 2 * (x - 0.5f).pow(2) + 0.5f
    }
}