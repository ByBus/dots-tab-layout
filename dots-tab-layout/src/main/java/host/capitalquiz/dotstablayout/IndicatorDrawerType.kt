package host.capitalquiz.dotstablayout

import host.capitalquiz.dotstablayout.indicatordrawer.CrawlingDotIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.DiscreteDotIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.ExpandingLineIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.FillingDotIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.GrowingCircleIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.JumpingDotIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.MovingDotIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.MovingLineIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.CompactJumpingDotIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.FadingDotIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.GrowingDotIndicator
import host.capitalquiz.dotstablayout.indicatordrawer.SwappingDotIndicator


internal enum class IndicatorDrawerType {
    DISCRETE_DOT_INDICATOR {
        override fun create(dotHolder: DotHolder) = DiscreteDotIndicator(dotHolder)
    },
    MOVING_DOT_INDICATOR {
        override fun create(dotHolder: DotHolder) = MovingDotIndicator(dotHolder)
    },
    FILLING_DOT_INDICATOR {
        override fun create(dotHolder: DotHolder) = FillingDotIndicator(dotHolder)
    },
    CRAWLING_DOT_INDICATOR {
        override fun create(dotHolder: DotHolder) = CrawlingDotIndicator(dotHolder)
    },
    MOVING_LINE_INDICATOR {
        override fun create(dotHolder: DotHolder) = MovingLineIndicator(dotHolder)
    },
    GROWING_CIRCLE_INDICATOR {
        override fun create(dotHolder: DotHolder) = GrowingCircleIndicator(dotHolder)
    },
    EXPANDING_LINE_INDICATOR {
        override fun create(dotHolder: DotHolder) = ExpandingLineIndicator(dotHolder)
    },
    JUMPING_DOT_INDICATOR {
        override fun create(dotHolder: DotHolder) = JumpingDotIndicator(dotHolder)
    },
    COMPACT_JUMPING_DOT_INDICATOR {
        override fun create(dotHolder: DotHolder) = CompactJumpingDotIndicator(dotHolder)
    },
    FADING_DOT_INDICATOR {
        override fun create(dotHolder: DotHolder) = FadingDotIndicator(dotHolder)
    },
    SWAPPING_DOT_INDICATOR {
        override fun create(dotHolder: DotHolder) = SwappingDotIndicator(dotHolder)
    },
    GROWING_DOT_INDICATOR {
        override fun create(dotHolder: DotHolder) = GrowingDotIndicator(dotHolder)
    }
    ;

    abstract fun create(dotHolder: DotHolder): DotsTabLayoutDrawer

   companion object {
       fun drawerByPosition(index: Int): IndicatorDrawerType =
           values().getOrElse(index) { DISCRETE_DOT_INDICATOR }
   }
}
