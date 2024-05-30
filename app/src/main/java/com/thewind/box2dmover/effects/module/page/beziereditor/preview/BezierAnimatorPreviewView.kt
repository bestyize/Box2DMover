package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement

class BezierAnimatorPreviewView(context: Context, attr: AttributeSet? = null) :
    FrameLayout(context, attr) {

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        setBackgroundColor(0x7Ff1f2f3)
    }

    private var animateSet: AnimatorSet? = null


    fun initAnimation(elementList: List<BezierAnimateElement>) {
        if (animateSet != null) {
            animateSet?.cancel()
        }
        children.forEach {
            removeView(it)
        }
        val containerWidth = width.toFloat()
        val containerHeight = height.toFloat()
        val aniList = mutableListOf<Animator>()
        elementList.forEach { element ->
            val view = createSurpriseRainElement(
                element = element,
                context = context,
                containerWidth = containerWidth,
                containerHeight = containerHeight
            )
            view?.let {
                val elementAnimationList = decodeToAnimationList(
                    element = element,
                    view = view,
                    containerWidth = containerWidth,
                    containerHeight = containerHeight
                )
                aniList.addAll(elementAnimationList)
                addView(view)
            }

        }
        animateSet = AnimatorSet().apply {
            playTogether(aniList)
        }
    }

    fun play() {
        animateSet?.let {
            if (it.isRunning) return
            it.start()
        }
    }


}