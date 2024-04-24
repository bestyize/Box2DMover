package com.thewind.box2dmover.effects.module.page.beziermove

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import com.thewind.box2dmover.effects.module.page.beziermove.animate.factory.createAnimateView
import com.thewind.box2dmover.effects.module.page.beziermove.animate.factory.decodeToAnimatorList
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimateItem
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimatePoint
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimateType
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimateViewParam
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimateViewType
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.BezierParam
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.CommonAnimateItem

class BezierView2(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {

    fun play(animateItems: List<AnimateItem> = mockList2()) {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        children.forEach {
            removeView(it)
        }

        doOnPreDraw {

            val allAnimatorList = mutableListOf<Animator>()
            animateItems.forEach { animateItem ->
                val view =
                    createAnimateView(animateItem, context, width.toFloat(), height.toFloat())
                addView(view)
                allAnimatorList.addAll(
                    decodeToAnimatorList(
                        animateItem,
                        view,
                        width.toFloat(),
                        height.toFloat()
                    )
                )
            }
            val set = AnimatorSet().apply {
                interpolator = LinearInterpolator()
                duration = 5000
                playTogether(allAnimatorList)
            }
            set.start()
        }


    }


}


fun mockList2(): List<AnimateItem> = listOf(
    AnimateItem(
        id = 0,
        viewParam = AnimateViewParam(
            type = AnimateViewType.NONE.type,
            width = 0.05f,
            height = 0.05f,
            initPos = AnimatePoint(x = 0.1f, y = 0.1f)
        ),
        animateList = listOf(
            CommonAnimateItem(
                type = AnimateType.MOVE.type,
                duration = 2000,
                delay = 0L,
                param = BezierParam(
                    start = AnimatePoint(
                        x = 0f, y = 0f
                    ),
                    end = AnimatePoint(
                        x = 0.5f, y = 0.5f
                    ),
                    control1 = AnimatePoint(
                        x = 1f, y = 1f
                    ),
                    control2 = AnimatePoint(
                        x = 0.5f, y = 1f
                    )
                )
            ),
//            CommonAnimateItem(
//                type = AnimateType.MOVE.type,
//                duration = 2000,
//                delay = 2001,
//                param = BezierParam(
//                    start = AnimatePoint(
//                        x = 0.4f, y = 0.4f
//                    ),
//                    end = AnimatePoint(
//                        x = 1f, y = 0.4f
//                    ),
//                    control1 = AnimatePoint(
//                        x = 0.6f, y = 0.4f
//                    ),
//                    control2 = AnimatePoint(
//                        x = 0.7f, y = 0.4f
//                    )
//                )
//            )
        )
    )
)