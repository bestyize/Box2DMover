package com.thewind.box2dmover.widget

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch


internal fun createCircleCountdownButton(
    context: Context,
    cx: Float = 450f,
    cy: Float = 1000f,
    size: Int = 75,
    duration: Int = 3000,
    onClose: () -> Unit = {},
    onFinish: () -> Unit = {}
) =
    ComposeView(context).apply {
        layoutParams = ViewGroup.LayoutParams(size, size)
        x = cx - size / 2
        y = cy - size / 2
        setContent {
            val scope = rememberCoroutineScope()
            CircleCountdownView(duration = duration, onClose = {
                scope.launch {
                    onClose.invoke()
                    visibility = View.GONE
                }

            }, onFinish = {
                onFinish.invoke()
                visibility = View.GONE
            })
        }
    }

@Composable
@Preview
private fun CircleCountdownView(
    circleWidth: Float = 5.5f,
    crossLineWidth: Float = circleWidth,
    crossLinePercent: Float = 0.15f,
    duration: Int = 3000,
    onClose: () -> Unit = {},
    onFinish: () -> Unit = {}
) {

    val circleRadius = remember {
        circleWidth / 2f
    }
    var play by remember {
        mutableStateOf(false)
    }

    val angleAnimation by animateFloatAsState(
        targetValue = if (play) 360f else 0f, finishedListener = {
            onFinish.invoke()
        },
        animationSpec = tween(durationMillis = duration, easing = LinearEasing),
        label = "angle"
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            drawCircle(
                color = Color.White,
                radius = size.minDimension / 2 - circleRadius,
                style = Stroke(width = circleWidth)
            )

            drawLine(
                color = Color.White,
                start = Offset(
                    x = size.center.x - size.minDimension * crossLinePercent,
                    y = size.center.y - size.minDimension * crossLinePercent
                ),
                end = Offset(
                    size.center.x + size.minDimension * crossLinePercent,
                    size.center.y + size.minDimension * crossLinePercent
                ),
                strokeWidth = crossLineWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = Color.White,
                start = Offset(
                    x = size.center.x + size.minDimension * crossLinePercent,
                    y = size.center.y - size.minDimension * crossLinePercent
                ),
                end = Offset(
                    size.center.x - size.minDimension * crossLinePercent,
                    size.center.y + size.minDimension * crossLinePercent
                ),
                strokeWidth = crossLineWidth,
                cap = StrokeCap.Round
            )

        }
        .drawWithContent {
            drawArc(
                color = Color.Gray,
                startAngle = -90f,
                sweepAngle = angleAnimation,
                size = Size(
                    width = size.minDimension - circleWidth,
                    size.minDimension - circleWidth
                ),
                useCenter = false,
                topLeft = Offset(
                    x = circleWidth / 2,
                    y = size.height / 2 - size.minDimension / 2 + circleRadius
                ),
                style = Stroke(width = circleWidth, cap = StrokeCap.Round)
            )
        }

        .clickable(interactionSource = remember {
            MutableInteractionSource()
        }, indication = null, onClick = {
            onClose.invoke()
        })
    )

    LaunchedEffect(key1 = Unit) {
        play = true
    }


}