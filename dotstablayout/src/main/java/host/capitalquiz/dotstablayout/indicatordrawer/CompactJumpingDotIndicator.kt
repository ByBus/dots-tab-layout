package host.capitalquiz.dotstablayout.indicatordrawer

import android.graphics.PointF
import host.capitalquiz.dotstablayout.DotHolder

internal class CompactJumpingDotIndicator(params: DotHolder) : JumpingIndicator(params) {
    private var offset = PointF()
    override fun calculateOffsetFromCenter(
        horizontalLength: Int,
        radius: Int,
        fraction: Float,
        dotsBetween: Int
    ): PointF {
        val verticalDistance = (if (dotsBetween == 0) params.dotSize() / 2 else params.dotSize()).toFloat()
        val totalPathLength = horizontalLength + 2 * verticalDistance
        val verticalSideFraction = verticalDistance / totalPathLength
        var xFraction = 0f
        val yFraction = if (fraction < verticalSideFraction) {
            fraction
        } else if (fraction > 1f - verticalSideFraction) {
            xFraction = 1f
            1f - fraction
        } else {
            val fromEnd = 1f - verticalSideFraction
            xFraction = fraction.mapDiapason(verticalSideFraction, fromEnd, 0f, 1f)
            verticalSideFraction
        }
        return offset.apply {
            set(radius * (xFraction - 0.5f) * 2, totalPathLength * yFraction)
        }
    }
}

private fun Float.mapDiapason(
    fromStart: Float,
    fromEnd: Float,
    toStart: Float,
    toEnd: Float,
): Float {
    val ratio = (this - fromStart) / (fromEnd - fromStart)
    return toStart + ratio * (toEnd - toStart)
}
