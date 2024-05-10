package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement

class BezierAnimatorPreviewView(context: Context, attr: AttributeSet? = null) :
    FrameLayout(context, attr) {

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        setBackgroundColor(0xFFF1F2F3.toInt())
    }

    fun play(elementList: List<BezierAnimateElement>) {
        children.forEach {
            removeView(it)
        }
        val containerWidth = width.toFloat()
        val containerHeight = height.toFloat()
        val aniList = mutableListOf<Animator>()
        elementList.forEach { element ->
            val view = createAnimatorView(
                element = element,
                context = context,
                containerWidth = containerWidth,
                containerHeight = containerHeight
            )
            val elementAnimationList = decodeToAnimatorList(
                element = element,
                view = view,
                containerWidth = containerWidth,
                containerHeight = containerHeight
            )
            aniList.addAll(elementAnimationList)
            addView(view)
        }
        val animationSet = AnimatorSet().apply {
            playTogether(aniList)
        }
        animationSet.start()

    }


}