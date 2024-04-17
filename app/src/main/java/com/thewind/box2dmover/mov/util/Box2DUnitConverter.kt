package com.thewind.box2dmover.mov.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thewind.box2dmover.app.App
import kotlin.math.PI

val Float.meterToPx: Float
    get() = this * meterPxFactor

val Float.meterToDp: Dp
    get() = (meterToPx / App.app.resources.displayMetrics.density).dp

val Int.pxToMeter: Float
    get() = this / meterPxFactor

val Float.pxToMeter: Float
    get() = this / meterPxFactor

val Float.pxToDp: Dp
    get() = (this / App.app.resources.displayMetrics.density).dp


private val meterPxFactor by lazy {
    App.app.resources.displayMetrics.heightPixels / BEST_SIM_METER
}

internal val screenWidth by lazy {
    App.app.resources.displayMetrics.widthPixels
}

internal val screenHeight by lazy {
    App.app.resources.displayMetrics.heightPixels
}

// box2d最佳模拟距离是0.1m-10m
private const val BEST_SIM_METER = 10f


val Float.b2dAngleToViewAngle: Float
    get() = ((this / Math.PI) * 180).rem(360).toFloat()

val Float.viewAngleToB2dAngle: Float
    get() = (rem(360) / 360f * 2 * PI).toFloat()
