package host.capitalquiz.dotstablayout

import android.widget.LinearLayout

internal interface DotHolder {
    fun dotLayoutParams(): LinearLayout.LayoutParams
    fun dotColor(): Int
    fun dotStrokeWidth(): Float
    fun dotSize(): Int
    fun currentDot(): Dot
    fun nextDot(): Dot
}