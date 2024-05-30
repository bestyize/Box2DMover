package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.box2dmover.animation.AnimatePathAsPosition
import com.thewind.box2dmover.animation.PathPosition
import com.thewind.box2dmover.effects.main.EffectPageActionButton
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimationType
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierParam
import com.thewind.box2dmover.effects.module.page.beziereditor.model.animationType
import com.thewind.box2dmover.effects.module.page.beziereditor.vm.BezierStudioViewModel
import com.thewind.box2dmover.ui.theme.ThemeColor4Half


@Composable
@Preview
fun BezierAnimatorPreviewComposePage() {

    val vm = viewModel(
        modelClass = BezierStudioViewModel::class.java,
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
    val state by vm.pageState.collectAsState()

    var play by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BezierPathViewerLayer(
            modifier = Modifier.align(Alignment.Center),
            elements = state.elements,
            containerWidth = state.previewContainerWidth,
            containerHeight = state.previewContainerHeight
        )
        Box(
            modifier = Modifier
                .size(
                    width = state.previewContainerWidth.dp, height = state.previewContainerHeight.dp
                )
                .background(Color(0x7Ff1f2f3))
                .align(Alignment.Center)
        ) {
            state.elements.forEach {
                ElementView(
                    element = it,
                    containerWidth = state.previewContainerWidth.toFloat(),
                    containerHeight = state.previewContainerHeight.toFloat(),
                    play = play
                )
            }
        }

        EffectPageActionButton(modifier = Modifier.align(Alignment.BottomCenter), "播放动画") {
            play = play.not()
        }

        Text(
            text = remember {
                "画布大小:${state.previewContainerWidth}x${state.previewContainerHeight}"
            },
            fontSize = 10.sp,
            color = Color.White,
            modifier = Modifier
                .padding(25.dp)
                .align(Alignment.TopEnd)
                .background(ThemeColor4Half, shape = RoundedCornerShape(3.dp))
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )

    }
}


@Composable
private fun ElementView(
    modifier: Modifier = Modifier,
    element: BezierAnimateElement,
    containerWidth: Float,
    containerHeight: Float,
    play: Boolean = false
) {
    var pos by remember {
        val point = element.list.filter { it.animationType == BezierAnimationType.MOVE }
            .sortedBy { it.delay }.getOrNull(0)?.param?.start ?: element.position
        val xPos = point.x * containerWidth
        val yPos = (1 - point.y) * containerHeight
        mutableStateOf(PathPosition(xPos, yPos))
    }
    element.list.filter { it.animationType == BezierAnimationType.MOVE }.forEach { param ->
        AnimatePathAsPosition(play = play,
            delay = param.delay.toInt(),
            duration = param.duration.toInt(),
            path = param.param.toMovePath(scaleX = containerWidth, scaleY = containerHeight),
            onChange = { x: Float, y: Float ->
                pos = pos.copy(x = x, y = y)
            })
    }

    var eleAlpha by remember {
        val alpha = element.list.filter { it.animationType == BezierAnimationType.ALPHA }
            .sortedBy { it.delay }.getOrNull(0)?.param?.start?.y ?: 1f
        mutableFloatStateOf(alpha)
    }

    element.list.filter { it.animationType == BezierAnimationType.ALPHA }.forEach { param ->
        AnimatePathAsPosition(play = play,
            delay = param.delay.toInt(),
            duration = param.duration.toInt(),
            path = param.param.toPath(),
            onChange = { x: Float, y: Float ->
                eleAlpha = y
            })
    }

    var eleScale by remember {
        val scale = element.list.filter { it.animationType == BezierAnimationType.SIZE }
            .getOrNull(0)?.param?.start?.y ?: 1f
        mutableFloatStateOf(scale)
    }

    element.list.filter { it.animationType == BezierAnimationType.SIZE }.forEach { param ->
        AnimatePathAsPosition(play = play,
            delay = param.delay.toInt(),
            duration = param.duration.toInt(),
            path = param.param.toPath(),
            onChange = { x: Float, y: Float ->
                eleScale = y
            })
    }

    var eleAngle by remember {
        val angle = element.list.filter { it.animationType == BezierAnimationType.ANGLE }
            .getOrNull(0)?.param?.start?.y ?: 0f
        mutableFloatStateOf(angle)
    }

    element.list.filter { it.animationType == BezierAnimationType.ANGLE }.forEach { param ->
        AnimatePathAsPosition(play = play,
            delay = param.delay.toInt(),
            duration = param.duration.toInt(),
            path = param.param.toPath(),
            onChange = { x: Float, y: Float ->
                eleAngle = y
            })
    }

    val size = remember(eleScale) {
        (element.width * eleScale).dp
    }

    Box(
        modifier = modifier
            .size(size)
            .offset(x = pos.x.dp - size / 2, y = pos.y.dp - size / 2)
            .rotate(eleAngle)
            .alpha(eleAlpha)
            .background(color = Color.Red)

    )
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


private fun BezierParam.toPath(scaleX: Float = 1f, scaleY: Float = 1f): Path {
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
