package com.thewind.box2dmover.effects.module.page.beziermove.animate.model

import androidx.annotation.Keep

@Keep
data class CommonAnimateItem(
    val type: Int = 0,
    val duration: Long = 0L,
    val delay: Long = 0L,
    val param: BezierParam = BezierParam()
)

val CommonAnimateItem.animateType: AnimateType
    get() = when (type) {
        0 -> AnimateType.MOVE
        1 -> AnimateType.ALPHA
        2 -> AnimateType.SIZE
        3 -> AnimateType.ROTATE
        else -> AnimateType.NONE
    }
