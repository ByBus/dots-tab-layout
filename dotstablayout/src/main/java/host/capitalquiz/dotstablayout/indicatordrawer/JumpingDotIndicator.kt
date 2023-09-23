package host.capitalquiz.dotstablayout.indicatordrawer

import android.graphics.PointF
import host.capitalquiz.dotstablayout.DotHolder
import kotlin.math.sqrt

internal class JumpingDotIndicator(params: DotHolder) : JumpingIndicator(params) {
    private var offset = PointF()
    override fun calculateOffsetFromCenter(
        horizontalLength: Int,
        radius: Int,
        fraction: Float,
        dotsBetween: Int
    ): PointF {
        val x = (fraction - 0.5f) * 2
        return offset.apply {
            set(x * radius, sqrt(1f - x * x) * radius)
        }
    }
}
