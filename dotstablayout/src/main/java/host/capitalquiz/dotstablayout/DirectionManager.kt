package host.capitalquiz.dotstablayout

internal class DirectionManager(private var onDirectionChanged: () -> Unit) {
    private var directionCheckSum = 0f
    private var direction = IDLE
    private var currentPosition = 0
    private var overscrolled = false

    fun updateDirection(newPosition: Int, offset: Float) {
        val newCheckSum = newPosition + offset
        val oldDirection = direction
        direction = when {
            newCheckSum > directionCheckSum -> FORWARD
            newCheckSum < directionCheckSum -> BACKWARD
            else -> IDLE
        }
        if (newPosition == currentPosition && offset != 0f) overscrolled = true
        if (direction != IDLE && direction != oldDirection) onDirectionChanged.invoke()

        directionCheckSum = newCheckSum
    }

    fun finish(currentPosition: Int) {
        this.currentPosition = currentPosition
        directionCheckSum = currentPosition.toFloat()
        if (overscrolled) direction = -direction
        overscrolled = false
    }

    fun isForward(): Boolean = direction == FORWARD
    fun isBackward(): Boolean = direction == BACKWARD

    companion object {
        private const val IDLE = 0
        private const val FORWARD = 1
        private const val BACKWARD = -1
    }
}