package com.thewind.box2dmover.mov.util

private const val METER_EQUAL_PX = 30f

val Int.pxToMeter: Float
    get() = this / METER_EQUAL_PX

val Float.pxToMeter: Float
    get() = this / METER_EQUAL_PX

val Float.meterToPx: Float
    get() = this * METER_EQUAL_PX

