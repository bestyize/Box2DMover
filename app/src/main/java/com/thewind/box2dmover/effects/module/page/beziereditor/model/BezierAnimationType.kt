package com.thewind.box2dmover.effects.module.page.beziereditor.model

import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimationType.NONE

enum class BezierAnimationType(val type: Int) {
    NONE(0), MOVE(1), ALPHA(2), SIZE(3), ANGLE(4)
}

val BezierAnimateItem.animationType: BezierAnimationType
    get() = BezierAnimationType.entries.find { it.type == this.type } ?: NONE