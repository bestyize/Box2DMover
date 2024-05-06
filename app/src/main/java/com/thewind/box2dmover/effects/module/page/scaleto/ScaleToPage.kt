package com.thewind.box2dmover.effects.module.page.scaleto

import android.animation.ValueAnimator
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.AccelerateInterpolator
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.animation.addListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
fun ScaleToPage() {
    Box(modifier = Modifier.fillMaxSize()) {

    }
}


private suspend fun doKeepRadioScaleAnimate(animateView: View, targetRect: Rect) = suspendCancellableCoroutine { cont ->
    val startWidth = animateView.width
    val startHeight = animateView.height
    val radio = startWidth.toFloat() / startHeight
    val targetHeight = targetRect.height()
    val targetWidth = targetHeight * radio

    val targetLeftMargin = targetRect.centerX() - targetWidth / 2

    val targetRightMargin = animateView.width - targetRect.centerX() - targetWidth / 2

    val scaleAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        interpolator = AccelerateInterpolator()
        duration = 1000
        addUpdateListener {
            val progress = it.animatedValue as Float
            val lp = animateView.layoutParams as MarginLayoutParams
            animateView.layoutParams = lp.apply {
                width = (startWidth - (startWidth - targetWidth) * progress).toInt()
                height = (startHeight - (startHeight - targetHeight) * progress).toInt()
                rightMargin = (targetRightMargin * progress).toInt()
                leftMargin = (targetLeftMargin * progress).toInt()
                topMargin = (targetRect.top * progress).toInt()
                bottomMargin = ((startHeight - targetRect.bottom) * progress).toInt()
            }
        }
        addListener(onEnd = {
            cont.resume(Unit)
        })
    }
    scaleAnimator.start()
}

