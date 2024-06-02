package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Path
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateItem
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimationType
import com.thewind.box2dmover.effects.module.page.beziereditor.model.animationType
import com.thewind.box2dmover.effects.module.page.beziereditor.model.orZero
import com.thewind.box2dmover.effects.module.page.beziereditor.model.toPath
import com.thewind.box2dmover.effects.module.page.beziereditor.model.toScalePath
import com.thewind.box2dmover.effects.module.page.beziereditor.preview.elementview.BalloonFlyElementView
import com.thewind.box2dmover.effects.module.page.beziereditor.preview.elementview.BezierBaseElementView
import com.thewind.box2dmover.effects.module.page.beziereditor.preview.elementview.SurpriseRainElementView


internal fun createSurpriseRainElement(
    element: BezierAnimateElement,
    context: Context,
    containerWidth: Float,
    containerHeight: Float,
    onClick: (() -> Unit)? = null
): BezierBaseElementView? {

    val list = element.list.sortedBy { it.delay }
    val position = list.firstOrNull { it.animationType == BezierAnimationType.MOVE }?.param?.start
        ?: element.position
    val scale =
        list.firstOrNull { it.animationType == BezierAnimationType.SIZE }?.param?.start?.y ?: 1f
    val angle =
        list.firstOrNull { it.animationType == BezierAnimationType.ANGLE }?.param?.start?.y ?: 0f
    val initAlpha =
        list.firstOrNull { it.animationType == BezierAnimationType.ALPHA }?.param?.start?.y ?: 1f
    val eleWidth = scale * element.width.toPx()
    val eleHeight = scale * element.height.toPx()

    return when (element.type) {
        0 -> SurpriseRainElementView(context).apply {
            clickListener = onClick
            x = position.x * containerWidth - eleWidth / 2
            y = position.y * containerHeight - eleWidth / 2
            rotation = angle
            alpha = initAlpha
            layoutParams = ViewGroup.LayoutParams(
                element.width.toPx(),
                if (element.height <= 0) element.width.toPx() else element.height.toPx()
            )
            scaleX = scale
            scaleY = scale
            bindData(
                element = element,
                containerWidth = containerWidth,
                containerHeight = containerHeight
            )
        }

        1 -> BalloonFlyElementView(context).apply {
            clickListener = onClick
            x = position.x * containerWidth - eleWidth / 2
            y = position.y * containerHeight - eleHeight / 2
            rotation = angle
            alpha = initAlpha
            layoutParams = ViewGroup.LayoutParams(
                element.width.toPx(),
                if (element.height <= 0) element.width.toPx() else element.height.toPx()
            )
            scaleX = scale
            scaleY = scale
            bindData(
                element = element
            )
        }

        else -> null
    }
}

internal fun decodeToAnimationList(
    element: BezierAnimateElement,
    view: BezierBaseElementView,
    containerWidth: Float,
    containerHeight: Float
): List<Animator> {

    val list = element.list.mapNotNull {
        when (it.animationType) {
            BezierAnimationType.MOVE -> buildPathAnimation(
                view, it, it.param.toPath(containerWidth, containerHeight)
            )

            BezierAnimationType.ALPHA -> buildAnimation(
                view, BezierBaseElementView.ANIM_ALPHA, it
            )

            BezierAnimationType.SIZE -> buildScaleAnimation(
                view, it, it.param.toScalePath()
            )

            BezierAnimationType.ANGLE -> buildAnimation(
                view, BezierBaseElementView.ANIM_ROTATE_Z, it
            )

            else -> null
        }
    }


    return list
}

private fun buildAnimation(
    view: View, propertyName: String, item: BezierAnimateItem, scale: Float = 1f
): Animator = ObjectAnimator.ofFloat(
    view, propertyName, item.param.start.y * scale, item.param.end.y * scale
).apply {
    val param = item.param
    interpolator = BezierInterpolator(
        param.control1.x.orZero,
        param.control1.y.orZero,
        param.control2.x.orZero,
        param.control2.y.orZero
    )
    startDelay = item.delay
    duration = item.duration
    setAutoCancel(true)
}

private fun buildScaleAnimation(
    view: BezierBaseElementView, item: BezierAnimateItem, path: Path
): Animator = ObjectAnimator.ofFloat(
    view, BezierBaseElementView.ANIM_SCALE_X, BezierBaseElementView.ANIM_SCALE_Y, path
).apply {
    interpolator = LinearInterpolator()
    startDelay = item.delay
    duration = item.duration
    setAutoCancel(true)
}

/**
 *  属性动画使用了反射拿get和set， 常规写法存在多次转换，无法在两次屏幕刷新之间完成位移动画，需要定制get 和set提高调用效率
 * @param view SurpriseRainElementView
 * @param item BezierAnimationItem
 * @param path Path?
 * @return Animator
 */
private fun buildPathAnimation(
    view: BezierBaseElementView, item: BezierAnimateItem, path: Path
): Animator = ObjectAnimator.ofFloat(
    view, BezierBaseElementView.ANIM_CENTER_X, BezierBaseElementView.ANIM_CENTER_Y, path
).apply {
    interpolator = LinearInterpolator()
    startDelay = item.delay
    duration = item.duration
    setAutoCancel(true)
}

fun Int.toPx(): Int {
    return (this * (Resources.getSystem().displayMetrics?.density ?: 2f) + 0.5f).toInt()
}

fun Float.toPx(): Float {
    return this * (Resources.getSystem().displayMetrics?.density ?: 2f) + 0.5f
}