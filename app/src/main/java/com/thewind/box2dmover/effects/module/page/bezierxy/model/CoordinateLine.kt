package com.thewind.box2dmover.effects.module.page.bezierxy.model

import androidx.compose.ui.graphics.Color

data class CoordinateLine(
    val start: CoordinatePoint = CoordinatePoint(),
    val end: CoordinatePoint = CoordinatePoint(),
    val lineWidth: Float = 1f,
    val lineColor: Color = Color.Black,
    val level: Int = 0
)
