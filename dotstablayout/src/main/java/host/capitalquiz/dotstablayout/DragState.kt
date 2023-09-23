package host.capitalquiz.dotstablayout

internal interface DragState {
    fun swipe(position: Int, fraction: Float)
    fun animate(position: Int)
    fun finish()
    fun indicatorPosition(consumer: (x: Float, y: Float, distanceFraction: Float) -> Unit)
}