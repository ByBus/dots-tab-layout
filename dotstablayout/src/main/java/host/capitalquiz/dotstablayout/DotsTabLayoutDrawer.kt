package host.capitalquiz.dotstablayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint

internal interface DotsTabLayoutDrawer {
    fun draw(canvas: Canvas, paint: Paint, dragState: DragState)
    fun createDot(context: Context, position: Int): Dot
    fun durationPerDot(dotPosition: Int): Long
}