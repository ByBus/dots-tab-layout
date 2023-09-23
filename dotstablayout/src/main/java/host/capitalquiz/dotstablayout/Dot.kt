package host.capitalquiz.dotstablayout

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.View

abstract class Dot @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    var index = 0

    protected val paint = Paint().apply {
        style = Style.STROKE
        color = Color.WHITE
        strokeWidth = 2f
        isAntiAlias = true
    }

    open var color: Int
        get() = paint.color
        set(value) {
            paint.color = value
        }

    var strokeWidth: Float
        get() = paint.strokeWidth
        set(value) {
            paint.strokeWidth = value
        }

    fun setStyle(style: Style) {
        paint.style = style
    }

    open var radius: Float = 10f
}