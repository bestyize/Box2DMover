package com.thewind.box2dmover.effects.module.page.beziereditor.model

import android.graphics.Path

data class BezierParam(
    val start: BezierPoint = BezierPoint(),
    val end: BezierPoint = BezierPoint(),
    val control1: BezierPoint = BezierPoint(),
    val control2: BezierPoint = BezierPoint()
)


internal fun BezierParam?.toPath(scaleX: Float = 1f, scaleY: Float = 1f): Path {
    this ?: return Path()
    return Path().apply {
        moveTo(start.x.orZero * scaleX, start.y.orZero * scaleY)
        cubicTo(
            control1.x.orZero * scaleX,
            control1.y.orZero * scaleY,
            control2.x.orZero * scaleX,
            control2.y.orZero * scaleY,
            end.x.orZero * scaleX,
            end.y.orZero * scaleY
        )
    }
}

internal fun BezierParam?.toScalePath(): Path {
    this ?: return Path()
    return Path().apply {
        moveTo(start.y.orZero, start.y.orZero)
        cubicTo(
            control1.y.orZero,
            control1.y.orZero,
            control2.y.orZero,
            control2.y.orZero,
            end.y.orZero,
            end.y.orZero
        )
    }
}

internal val Float?.orZero: Float
    get() = this ?: 0f
