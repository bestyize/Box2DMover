package com.thewind.box2dmover.effects.module.page.beziereditor.model

data class BezierParam(
    val start: BezierPoint = BezierPoint(),
    val end: BezierPoint = BezierPoint(),
    val control1: BezierPoint = BezierPoint(),
    val control2: BezierPoint = BezierPoint()
)