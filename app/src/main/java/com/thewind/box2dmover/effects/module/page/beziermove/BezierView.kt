package com.thewind.box2dmover.effects.module.page.beziermove

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.view.children
import kotlin.math.abs

class BezierView(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {

    init {
        setWillNotDraw(false)
    }

    private var path: Path? = null
    private var pathPainter = Paint().apply {
        color = Color.RED
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path?.let {
            canvas.drawPath(it, pathPainter)
        }
    }

    fun play2(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        cx1: Float,
        cy1: Float,
        cx2: Float,
        cy2: Float
    ) {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        children.forEach {
            removeView(it)
        }

        val w = 100
        val h = 100

        val view = View(context).apply {
            x = startX - w / 2
            y = startY - h / 2
            layoutParams = MarginLayoutParams(w, h).apply {
                topMargin = -h / 2
                leftMargin - w / 2
            }
            setBackgroundColor(Color.BLUE)
        }
        addView(view)


        path = Path()
        path?.run {
            moveTo(startX, startY)
            cubicTo(cx1, cy1, cx2, cy2, endX, endY)
            var startTime = 0L
            val aniX = ObjectAnimator.ofFloat(Position(), "x", "y", this).apply {
                interpolator = LinearInterpolator()
                this.addUpdateListener {
                    val xPos = it.getAnimatedValue("x") as Float
                    val yPos = it.getAnimatedValue("y") as Float
                    view.x = xPos - view.width / 2
                    view.y = yPos - view.height / 2
                    val current = it.currentPlayTime
                    if (current != 0L && startTime == 0L) {
                        startTime = current
                    }
                    val realTimeOffset = current - startTime
                    Log.i("read", "$current, ${it.animatedValue}")
                    if (realTimeOffset in 1500..3500) {
                        view.setBackgroundColor(Color.BLUE)
                    }
                    view.layoutParams = view.layoutParams.apply {
                        width = (200 * abs(it.animatedFraction - 0.5f)).toInt()
                        height = (200 * abs(0.5f - it.animatedFraction)).toInt()
                    }
                }
            }

            AnimatorSet().apply {
                duration = 5000
                playTogether(aniX)
                start()
            }
        }

        postInvalidate()

    }
}

class Position {
    var x: Float = 0f
    var y: Float = 0f
}