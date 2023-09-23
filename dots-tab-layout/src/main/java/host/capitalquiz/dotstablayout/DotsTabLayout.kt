package host.capitalquiz.dotstablayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class DotsTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs), DefaultLifecycleObserver, DotHolder {
    private var dotSize = 0
    private var distributeEvenly = false
    private var dotStrokeWidth = 0f
    private var clickableDots = false
    private var dotHorizontalPadding = 0
    private var currentDot = 0
    private var nextDot = 0
    private var direction = DirectionManager(::swapDots)
    private var viewPager: ViewPager2? = null
    private var dragState: DragState = Idle()
    private lateinit var drawer: DotsTabLayoutDrawer
    private val paint = Paint().apply {
        isAntiAlias = true
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.DotsTabLayout) {
            dotSize = getDimensionPixelSize(R.styleable.DotsTabLayout_dotSize, 32)
            distributeEvenly = getBoolean(R.styleable.DotsTabLayout_evenly, false)
            clickableDots = getBoolean(R.styleable.DotsTabLayout_dotsClickable, false)
            dotHorizontalPadding =
                getDimensionPixelSize(R.styleable.DotsTabLayout_dotsHorizontalPadding, 4)
            paint.color = getColor(R.styleable.DotsTabLayout_baseColor, Color.WHITE)
            dotStrokeWidth = getDimension(R.styleable.DotsTabLayout_dotStrokeWidth, 2f)
            val dotsNumber = getInt(R.styleable.DotsTabLayout_dotsNumber, 0)
            val indicatorTypeIndex = getInt(R.styleable.DotsTabLayout_indicatorType, 0)
            drawer =
                IndicatorDrawerType.drawerByPosition(indicatorTypeIndex).create(this@DotsTabLayout)
            if (dotsNumber > 0) createDots(dotsNumber)
        }
    }

    private val clickPositionAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            invalidate()
        }
        doOnEnd {
            setState(Idle().apply { finish() })
        }
    }

    fun attachTo(viewPager: ViewPager2, lifecycleOwner: LifecycleOwner) {
        removeAllViews()
        this.viewPager = viewPager
        createDots(viewPager.adapter?.itemCount ?: viewPager.childCount)
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private fun createDots(count: Int) {
        for (i in 0 until count) {
            val dot = drawer.createDot(context, i)
            if (clickableDots) {
                dot.setOnClickListener {
                    viewPager?.currentItem = i
                }
            }
            addView(dot)
        }
    }

    override fun dotLayoutParams(): LayoutParams {
        return if (distributeEvenly)
            LayoutParams(LayoutParams.MATCH_PARENT, dotSize, 1f)
        else
            LayoutParams(dotSize + dotHorizontalPadding * 2, dotSize)
    }

    override fun dotColor(): Int = paint.color

    override fun dotStrokeWidth(): Float = dotStrokeWidth

    override fun dotSize(): Int = dotSize

    override fun nextDot(): Dot = getChildAt(nextDot) as Dot

    override fun currentDot(): Dot = getChildAt(currentDot) as Dot

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawer.draw(canvas, paint, dragState)
    }

    private fun updateNextDot(step: Int) {
        nextDot = when {
            direction.isForward() -> currentDot + step
            direction.isBackward() -> currentDot - step
            else -> nextDot
        }.coerceIn(0, childCount - 1)
    }

    private fun swapDots() {
        currentDot = nextDot.also { nextDot = currentDot }
    }

    private fun isScrolling(): Boolean = viewPager?.scrollState == ViewPager2.SCROLL_STATE_SETTLING

    private fun updateCurrentFromPager() {
        currentDot = viewPager?.currentItem ?: nextDot
    }

    private fun setState(drag: DragState) {
        dragState = drag
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        viewPager?.registerOnPageChangeCallback(pageChangeCallback)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        viewPager?.unregisterOnPageChangeCallback(pageChangeCallback)
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int,
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            dragState.swipe(position, positionOffset)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            dragState.animate(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager2.SCROLL_STATE_IDLE) dragState.finish()
        }
    }

    private abstract inner class BaseDragState : DragState {
        override fun indicatorPosition(consumer: (x: Float, y: Float, distFraction: Float) -> Unit) {
            val current = getChildAt(currentDot)
            val next = getChildAt(nextDot)
            val distanceBetweenDots = abs(current.left - next.left)
            val fraction = animationFraction()
            val xPosition = current.left + current.width / 2f + distanceBetweenDots * fraction
            val yPosition = current.top + current.height / 2f
            consumer.invoke(xPosition, yPosition, fraction)
        }

        abstract fun animationFraction(): Float
        override fun swipe(position: Int, fraction: Float) = Unit
        override fun animate(position: Int) = Unit
        override fun finish() {
            updateCurrentFromPager()
            direction.finish(currentDot)
        }
    }

    private inner class Idle : BaseDragState() {
        override fun animationFraction(): Float = 0f

        override fun swipe(position: Int, fraction: Float) {
            if (isScrolling().not()) {
                ByItemSwipe().apply {
                    swipe(position, fraction)
                    setState(this)
                }
            }
        }

        override fun animate(position: Int) {
            ByDotClick().apply {
                animate(position)
                setState(this)
            }
        }
    }

    private inner class ByItemSwipe : BaseDragState() {
        private var scrollOffset = 0f
        override fun animationFraction(): Float =
            scrollOffset - if (direction.isBackward()) 1 else 0

        override fun swipe(position: Int, fraction: Float) {
            direction.updateDirection(position, fraction)
            updateNextDot(1)
            scrollOffset = fraction
            invalidate()
        }

        override fun finish() {
            super.finish()
            setState(Idle())
        }
    }

    private inner class ByDotClick : BaseDragState() {
        override fun animationFraction(): Float =
            (clickPositionAnimator.animatedValue as Float) * if (direction.isBackward()) -1 else 1

        override fun animate(position: Int) {
            direction.updateDirection(position, 0f)
            val step = abs(currentDot - position)
            updateNextDot(step)
            clickPositionAnimator.duration = drawer.durationPerDot(step)
            clickPositionAnimator.start()
        }

        override fun finish() = Unit
    }
}
