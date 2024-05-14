package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
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
    Canvas(modifier = modifier.size(containerWidth.dp, containerHeight.dp)) {
        elements.forEach { element ->
            element.list.filter { it.animationType == BezierAnimationType.MOVE }
                .forEach { animation ->
                    val path = animation.param.toMovePath(
                        containerWidth.toPx().toFloat(), containerHeight.toPx().toFloat()
                    )
                    drawPath(
                        path = path,
                        color = Color.Red,
                        style = Stroke(width = 2f, cap = StrokeCap.Round)
                    )
                }
        }
    }
}

private fun BezierParam.toMovePath(scaleX: Float = 1f, scaleY: Float = 1f): Path {
    return Path().apply {
        moveTo(start.x * scaleX, (1 - start.y) * scaleY)
        cubicTo(
            control1.x * scaleX,
            (1 - control1.y) * scaleY,
            control2.x * scaleX,
            (1 - control2.y) * scaleY,
            end.x * scaleX,
            (1 - end.y) * scaleY
        )
    }
}