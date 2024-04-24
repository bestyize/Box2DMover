package com.thewind.box2dmover.effects.module.page.beziermove.animate.model

import androidx.annotation.Keep

@Keep
data class AnimateViewParam(
    val type: Int = 0,
    val width: Float = 0f,
    val height: Float = 0f,
    val initPos: AnimatePoint = AnimatePoint(),
    val iconMd5: String? = null,
    val icon: String? = null
)

val AnimateViewParam.viewType: AnimateViewType
    get() = when (type) {
        0 -> AnimateViewType.IMAGE
        else -> AnimateViewType.NONE
    }