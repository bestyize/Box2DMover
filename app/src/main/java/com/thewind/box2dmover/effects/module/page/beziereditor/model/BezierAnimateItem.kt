package com.thewind.box2dmover.effects.module.page.beziereditor.model

data class BezierAnimateItem(
    val type: Int = 0,
    val delay: Long = 0,
    val duration: Long = 0L,
    val param: BezierParam = BezierParam()
)
