package com.thewind.box2dmover.rain2

import com.thewind.box2dmover.rain2.effect.createRandomDownSnow
import com.thewind.box2dmover.rain2.effect.createRandomUpSnow


fun createSnowByType(type: Int) = when (type) {
    0 -> createRandomDownSnow()
    1 -> createRandomUpSnow()
    else -> emptyList()
}

