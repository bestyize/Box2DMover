package com.thewind.box2dmover.effects.module.page.beziermove.animate.factory

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Path
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimateItem
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimatePosition
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimateType
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.BezierParam
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.animateType


fun decodeToAnimatorList(
    animateItem: AnimateItem,
    view: View,
    containerWidth: Float,
    containerHeight: Float
): List<Animator> {

    val moveAnimateList =
        animateItem.animateList.filter { it.animateType == AnimateType.MOVE }.map { item ->
            val path = item.param.toPath(containerWidth, containerHeight)
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
        animateItem.animateList.filter { it.animateType == AnimateType.ALPHA }.map { item ->
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
        animateItem.animateList.filter { it.animateType == AnimateType.SIZE }.map { item ->
            val path = item.param.toPath()
            ObjectAnimator.ofFloat(AnimatePosition(), "x", "y", path).apply {
                interpolator = LinearInterpolator()
                startDelay = item.delay
                duration = item.duration
                addUpdateListener {
                    val scale = it.getAnimatedValue("y") as Float
                    view.layoutParams = view.layoutParams.apply {
                        width = (animateItem.viewParam.width * containerWidth * scale).toInt()
                        height = (animateItem.viewParam.height * containerHeight * scale).toInt()
                    }
                }
            }
        }

    val rotateAnimateList =
        animateItem.animateList.filter { it.animateType == AnimateType.ROTATE }.map { item ->
            val path = item.param.toPath()
            ObjectAnimator.ofFloat(AnimatePosition(), "x", "y", path).apply {
                interpolator = LinearInterpolator()
                startDelay = item.delay
                duration = item.duration
                addUpdateListener {
                    val angle = it.getAnimatedValue("y") as Float
                    Log.i("[read", "angle = $angle")
                    view.rotation = 360 * angle
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
            end.y * scaleY
        )
    }
}