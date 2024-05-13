package com.thewind.box2dmover.effects.module.page.beziereditor.model

import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimationType.NONE

enum class BezierAnimationType(val type: Int, val title: String) {
    NONE(0, "无"), MOVE(1, "运动"), ALPHA(2, "透明度"), SIZE(3, "缩放"), ANGLE(4, "旋转")
}

val BezierAnimateItem.animationType: BezierAnimationType
    get() = BezierAnimationType.entries.find { it.type == this.type } ?: NONE