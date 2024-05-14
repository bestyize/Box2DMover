package com.thewind.box2dmover.animation

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import kotlin.math.abs


@Composable
fun AnimatePathAsPosition(
    play: Boolean,
    delay: Int,
    duration: Int,
    path: Path,
    onChange: (x: Float, y: Float) -> Unit = { _, _ -> }
) {
    animateFloatAsState(
        if (play) 0f else 1f, label = "xxx", animationSpec = tween(
            durationMillis = duration,
            delayMillis = delay,
            easing = PathMoveEasing(path = path, onChange = onChange, stableStep = 2000)
        )
    )
}

data class PathPosition(val x: Float, val y: Float)

@Immutable
class PathMoveEasing(
    path: Path,
    precision: Float = 0.005f,
    stableStep: Int = 0,
    private val onChange: (x: Float, y: Float) -> Unit = { _, _ -> }
) : Easing {
    private val offsetX: FloatArray
    private val offsetY: FloatArray

    init {
        val pathMeasure = PathMeasure()
        pathMeasure.setPath(path, false)

        val pathLength: Float = pathMeasure.length
        require(pathLength > 0) {
            "Path cannot be zero in length. " + "Ensure that supplied Path starts at [0,0] and ends at [1,1]"
        }
        val numPoints: Int =
            if (stableStep > 0) stableStep else (pathLength / precision).toInt() + 1

        offsetX = FloatArray(numPoints) { 0f }
        offsetY = FloatArray(numPoints) { 0f }

        for (i in 0 until numPoints) {
            val distance = i * pathLength / (numPoints - 1)
            val offset = pathMeasure.getPosition(distance)
            offsetX[i] = offset.x
            offsetY[i] = offset.y
        }
    }

    override fun transform(fraction: Float): Float {
        if (fraction <= 0.0f) {
            return 0.0f
        } else if (fraction >= 1.0f) {
            return 1.0f
        }
        val indexX = abs(fraction * (offsetX.size - 1)).toInt()
        val indexY = abs(fraction * (offsetY.size - 1)).toInt()
        val x = offsetX[indexX]
        val y = offsetY[indexY]
        onChange.invoke(x, y)
        return fraction
    }
}