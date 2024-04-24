package com.thewind.box2dmover.effects.module.page.beziermove.animate.model

import androidx.annotation.Keep

@Keep
data class BezierParam(
    val start: AnimatePoint = AnimatePoint(),
    val end: AnimatePoint = AnimatePoint(),
    val control1: AnimatePoint = AnimatePoint(),
    val control2: AnimatePoint = AnimatePoint()
)