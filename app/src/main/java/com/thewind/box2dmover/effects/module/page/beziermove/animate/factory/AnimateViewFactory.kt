package com.thewind.box2dmover.effects.module.page.beziermove.animate.factory

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimateItem
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.AnimateViewType
import com.thewind.box2dmover.effects.module.page.beziermove.animate.model.viewType

fun createAnimateView(
    animateItem: AnimateItem,
    context: Context,
    containerWidth: Float,
    containerHeight: Float
): View {

    return when (animateItem.viewParam.viewType) {
        AnimateViewType.IMAGE -> createImageView(
            context,
            animateItem,
            containerWidth,
            containerHeight
        )

        else -> createColorView(context, animateItem, containerWidth, containerHeight)
    }

}


private fun createImageView(
    context: Context,
    animateItem: AnimateItem,
    containerWidth: Float,
    containerHeight: Float
): View = ImageView(context).apply {
    val initWidth = animateItem.viewParam.width * containerWidth
    val initHeight = animateItem.viewParam.height * containerHeight
    layoutParams = ViewGroup.LayoutParams(
        initWidth.toInt(),
        initHeight.toInt()
    )
    x = animateItem.viewParam.initPos.x * containerWidth - initWidth / 2
    y = animateItem.viewParam.initPos.y * containerHeight - initHeight / 2
}


private fun createColorView(
    context: Context,
    animateItem: AnimateItem,
    containerWidth: Float,
    containerHeight: Float
): View = View(context).apply {
    val initWidth = animateItem.viewParam.width * containerWidth
    val initHeight = animateItem.viewParam.height * containerHeight
    layoutParams = ViewGroup.LayoutParams(
        initWidth.toInt(),
        initHeight.toInt()
    )
    x = animateItem.viewParam.initPos.x * containerWidth - initWidth / 2
    y = animateItem.viewParam.initPos.y * containerHeight - initHeight / 2
    setBackgroundColor(Color.RED)
}