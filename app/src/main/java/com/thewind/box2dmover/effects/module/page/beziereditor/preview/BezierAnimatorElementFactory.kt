package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Path
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.thewind.box2dmover.effects.module.page.beziereditor.model.AnimatePosition
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimationType
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierParam
import com.thewind.box2dmover.effects.module.page.beziereditor.model.animationType


fun createAnimatorView(
    element: BezierAnimateElement, context: Context, containerWidth: Float, containerHeight: Float
): View {
    val eleScale =
        element.list.filter { it.animationType == BezierAnimationType.SIZE }.sortedBy { it.delay }
            .getOrNull(0)?.param?.start?.y ?: 1f
    val eleSize = eleScale * element.width.toPx()

    val position =
        element.list.filter { it.animationType == BezierAnimationType.MOVE }.sortedBy { it.delay }
            .getOrNull(0)?.param?.start ?: element.position

    val startX = position.x * containerWidth - eleSize / 2
    val startY = (1 - position.y) * containerHeight - eleSize / 2

    val initAngle =
        element.list.filter { it.animationType == BezierAnimationType.ANGLE }.sortedBy { it.delay }
            .getOrNull(0)?.param?.start?.y ?: 0f

    val initAlpha =
        element.list.filter { it.animationType == BezierAnimationType.ALPHA }.sortedBy { it.delay }
            .getOrNull(0)?.param?.start?.y ?: 1f

    return View(context).apply {
        x = startX
        y = startY
        rotation = initAngle
        alpha = initAlpha
        layoutParams = ViewGroup.LayoutParams(eleSize.toInt(), eleSize.toInt())
        setBackgroundColor(Color.RED)
    }
}

fun decodeToAnimatorList(
    element: BezierAnimateElement, view: View, containerWidth: Float, containerHeight: Float
): List<Animator> {

    val moveAnimateList =
        element.list.filter { it.animationType == BezierAnimationType.MOVE }.map { item ->
            val path = item.param.toMovePath(containerWidth, containerHeight)
            ObjectAnimator.ofFloat(AnimatePosition(), "x", "y", path).apply {
                interpolator = LinearInterpolator()
                startDelay = item.delay
                duration = item.duration
                addUpdateListener {
                    val cx = it.getAnimatedValue("x") as Float
                    val cy = it.getAnimatedValue("y") as Float
                    view.x = cx - view.width / 2
                    view.y = cy - view.height / 2
                }
            }

        }

    val alphaAnimateList =
        element.list.filter { it.animationType == BezierAnimationType.ALPHA }.map { item ->
            val path = item.param.toPath()
            ObjectAnimator.ofFloat(AnimatePosition(), "x", "y", path).apply {
                interpolator = LinearInterpolator()
                startDelay = item.delay
                duration = item.duration
                addUpdateListener {
                    view.alpha = it.getAnimatedValue("y") as Float
                }
            }
        }


    val sizeAnimateList =
        element.list.filter { it.animationType == BezierAnimationType.SIZE }.map { item ->
            val path = item.param.toPath()
            ObjectAnimator.ofFloat(AnimatePosition(), "x", "y", path).apply {
                interpolator = LinearInterpolator()
                startDelay = item.delay
                duration = item.duration
                addUpdateListener {
                    val scale = it.getAnimatedValue("y") as Float
                    val size = (element.width.toPx() * scale).toInt()
                    view.layoutParams = view.layoutParams.apply {
                        width = size
                        height = size
                    }
                }
            }
        }

    val rotateAnimateList =
        element.list.filter { it.animationType == BezierAnimationType.ANGLE }.map { item ->
            val path = item.param.toPath()
            ObjectAnimator.ofFloat(AnimatePosition(), "x", "y", path).apply {
                interpolator = LinearInterpolator()
                startDelay = item.delay
                duration = item.duration
                addUpdateListener {
                    val angle = it.getAnimatedValue("y") as Float
                    Log.i("[read", "angle = $angle")
                    view.rotation = angle
                }
            }
        }

    return moveAnimateList + alphaAnimateList + sizeAnimateList + rotateAnimateList
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
            end.y* scaleY
        )
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

fun Int.toPx(): Int {
    return (this * (Resources.getSystem().displayMetrics?.density ?: 2f) + 0.5f).toInt()
}