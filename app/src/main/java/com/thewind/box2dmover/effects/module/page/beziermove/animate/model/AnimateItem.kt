package com.thewind.box2dmover.effects.module.page.beziermove.animate.model

import androidx.annotation.Keep

@Keep
data class AnimateItem(
    val id: Long = 0L,
    val viewParam: AnimateViewParam = AnimateViewParam(),
    val animateList: List<CommonAnimateItem> = emptyList()
)


