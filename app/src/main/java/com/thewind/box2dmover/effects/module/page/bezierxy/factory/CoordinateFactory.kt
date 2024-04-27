package com.thewind.box2dmover.effects.module.page.bezierxy.factory

import com.thewind.box2dmover.effects.module.page.bezierxy.model.CoordinateLine
import com.thewind.box2dmover.effects.module.page.bezierxy.model.CoordinatePoint
import com.thewind.box2dmover.effects.module.page.bezierxy.model.CoordinateSystem

fun createCoordinateSystem(width: Float, height: Float, largeBlockSize: Float): CoordinateSystem {


    val lines = mutableListOf<CoordinateLine>()

    var currentColumn = 0f

    while (currentColumn < height) {
        var currentRow = 0f
        while (currentRow < width) {
            lines.add(
                CoordinateLine(
                    start = CoordinatePoint(currentRow, currentColumn),
                    end = CoordinatePoint()
                )
            )
            currentRow += largeBlockSize
        }
        currentColumn += largeBlockSize
    }



    return CoordinateSystem().apply {

    }
}