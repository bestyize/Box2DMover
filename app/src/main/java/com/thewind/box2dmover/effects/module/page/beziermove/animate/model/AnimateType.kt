package com.thewind.box2dmover.effects.module.page.beziermove.animate.model

import androidx.annotation.Keep

@Keep
enum class AnimateType(val type: Int) {
    NONE(-1),
    MOVE(0),
    ALPHA(1),
    SIZE(2),
    ROTATE(3)
}