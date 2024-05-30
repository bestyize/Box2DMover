package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.thewind.box2dmover.app.App
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimationType
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierParam
import com.thewind.box2dmover.effects.module.page.beziereditor.model.animationType

@Composable
fun BezierPathViewerLayer(
    modifier: Modifier,
    elements: List<BezierAnimateElement>,
    containerWidth: Int,
    containerHeight: Int
) {
    val screenWidth = remember(containerWidth) {
        if (containerWidth == -1) (App.widthPx / App.app.resources.displayMetrics.density).toInt() else containerWidth
    }

    val screenHeight = remember(containerHeight) {
        if (containerHeight == -1) (App.heightPx / App.app.resources.displayMetrics.density).toInt() else containerHeight
    }

    Canvas(modifier = modifier.size(screenWidth.dp, screenHeight.dp)) {
        elements.forEach { element ->
            element.list.filter { it.animationType == BezierAnimationType.MOVE }
                .forEach { animation ->
                    val path = animation.param.toMovePath(
                        screenWidth.toPx().toFloat(), screenHeight.toPx().toFloat()
                    )
                    drawPath(
                        path = path,
                        color = Color.Red,
                        style = Stroke(width = 5f, cap = StrokeCap.Round)
                    )
                }
        }
    }
}

private fun BezierParam.toMovePath(scaleX: Float = 1f, scaleY: Float = 1f): Path {
    return Path().apply {
        moveTo(start.x * scaleX, start.y * scaleY)
        cubicTo(
            control1.x * scaleX,
            control1.y * scaleY,
            control2.x * scaleX,
            control2.y * scaleY,
            end.x * scaleX,
            end.y * scaleY
        )
    }
}