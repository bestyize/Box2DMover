package com.thewind.box2dmover.effects.module.page.beziereditor.preview.elementview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import com.thewind.box2dmover.R
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement
import com.thewind.box2dmover.effects.module.page.beziereditor.preview.toPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BalloonFlyElementView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, attrDef: Int = 0, attrStyle: Int = 0
) : BezierBaseElementView(context, attributeSet, attrDef, attrStyle) {

    private val drawScope = CoroutineScope(Dispatchers.Main)

    var clickListener: (() -> Unit)? = null

    fun bindData(
        element: BezierAnimateElement, containerWidth: Float, containerHeight: Float
    ) {
        drawScope.launch {
            val bm = suspendBuildBitmap(element) ?: return@launch
            val adImage = ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
                )
                scaleType = ImageView.ScaleType.FIT_XY

                setImageBitmap(bm)
            }

            addView(adImage)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        drawScope.cancel()
    }


    private suspend fun suspendBuildBitmap(element: BezierAnimateElement): Bitmap? =
        withContext(Dispatchers.IO) {
            buildBitmap(element)
        }


    private fun buildBitmap(element: BezierAnimateElement): Bitmap? {

        if (element.width < element.topImageWidth || element.width < element.imageWidth
            || element.height < element.topImageHeight || element.height < element.imageHeight
        ) {
            return null
        }

        val bm = Bitmap.createBitmap(
            element.width.toPx(),
            element.height.toPx(),
            Bitmap.Config.ARGB_8888
        )

        val topImage = BitmapFactory.decodeResource(context.resources, R.drawable.balloon)
        val topSrcRect = Rect(0, 0, topImage.width, topImage.height)
        val topLeftMargin = (element.width - element.topImageWidth).toPx() / 2
        val topDstRect =
            Rect(
                topLeftMargin,
                0,
                element.width.toPx() - topLeftMargin,
                element.topImageHeight.toPx()
            )

        val bottomImage = BitmapFactory.decodeResource(context.resources, R.drawable.chengzi)
        val bottomSrcRect = Rect(0, 0, bottomImage.width, bottomImage.height)
        val bottomLeftMargin = (element.width - element.imageWidth).toPx() / 2
        val bottomDstRect = Rect(
            bottomLeftMargin,
            (element.height - element.imageHeight).toPx(),
            element.width.toPx() - bottomLeftMargin,
            element.height.toPx()
        )


        val linePaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = Color.RED
            strokeWidth = (1.5f).toPx()
        }
        val canvas = Canvas(bm)

        canvas.drawBitmap(topImage, topSrcRect, topDstRect, null)
        canvas.drawBitmap(bottomImage, bottomSrcRect, bottomDstRect, null)
        val lineX = element.width.toPx() / 2f

        canvas.drawLine(
            lineX,
            element.topImageHeight.toPx().toFloat(),
            lineX,
            (element.height - element.imageHeight).toPx().toFloat(),
            linePaint
        )

        val boundPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = 0X7f0000FF
            strokeWidth = 2f
        }

        canvas.drawRect(Rect(0, 0, element.width.toPx(), element.height.toPx()), boundPaint)

        canvas.drawRect(topDstRect, boundPaint)
        canvas.drawRect(bottomDstRect, boundPaint)

        topImage.recycle()
        bottomImage.recycle()
        return bm
    }


}