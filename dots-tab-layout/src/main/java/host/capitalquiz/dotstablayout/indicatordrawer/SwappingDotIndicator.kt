package host.capitalquiz.dotstablayout.indicatordrawer

import android.graphics.PointF
import android.view.animation.Interpolator
import host.capitalquiz.dotstablayout.Dot
import host.capitalquiz.dotstablayout.DotHolder
import kotlin.math.abs
import kotlin.math.pow

internal class SwappingDotIndicator(
    params: DotHolder,
    private val indicatorInterpolator: Interpolator = IndicatorParabolicInterpolator(),
    private val dotInterpolator: Interpolator = DotParabolicInterpolator(),
) : JumpingIndicator(params) {
    private var offset = PointF()
    override fun calculateOffsetFromCenter(
        horizontalLength: Int,
        radius: Int,
        fraction: Float,
        dotsBetween: Int,
    ): PointF {
        val x = (fraction - 0.5f) * 2
        return offset.apply {
            set(x * radius, 0f)
        }
    }

    override fun dotRadius(current: Dot, fraction: Float): Float =
        super.dotRadius(current, fraction) *
                dotInterpolator.getInterpolation(fraction)

    override fun indicatorRadius(current: Dot, fraction: Float): Float =
        super.indicatorRadius(current, fraction) *
                indicatorInterpolator.getInterpolation(fraction)

}

private class DotParabolicInterpolator : Interpolator {
    override fun getInterpolation(input: Float): Float {
        val x = abs(input)
        return 0.4f * (x - 0.5f).pow(2) + 0.9f
    }
}

private class IndicatorParabolicInterpolator : Interpolator {
    override fun getInterpolation(input: Float): Float {
        val x = abs(input)
        return -0.4f * (x - 0.5f).pow(2) + 1.1f
    }
}