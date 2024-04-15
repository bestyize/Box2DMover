package com.thewind.box2dmover.rain

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BlendMode
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.thewind.box2dmover.app.toast
import com.thewind.box2dmover.mov.factory.createBody
import com.thewind.box2dmover.mov.factory.createWorld
import com.thewind.box2dmover.mov.model.BodyItem
import com.thewind.box2dmover.mov.model.CircleShape
import com.thewind.box2dmover.mov.model.PolygonShape
import com.thewind.box2dmover.mov.model.WorldParam
import com.thewind.box2dmover.mov.util.detectTouchBody
import com.thewind.box2dmover.mov.util.meterToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class RainSurfaceView(context: Context, attributeSet: AttributeSet? = null) : SurfaceView(context, attributeSet), SurfaceHolder.Callback2{
    private var holder: SurfaceHolder? = null

    private val rainScope = CoroutineScope(Dispatchers.Main)

    private var worldParam: WorldParam? = null

    private var world: World = createWorld(WorldParam(yGravity = 10f))

    private val bodyList: MutableList<Body> = mutableListOf()

    private val rectPainter = Paint().apply {
        color = Color.RED
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val circlePainter = Paint().apply {
        color = Color.BLUE
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    init {
        setZOrderOnTop(true)
        this.holder = getHolder()
        this.holder?.setFormat(PixelFormat.TRANSPARENT)
        this.holder?.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    override fun surfaceRedrawNeeded(holder: SurfaceHolder) {

    }



    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action ?: return super.onTouchEvent(event)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                val body = bodyList.find { it.detectTouchBody(event) }
                if (body != null) {
                    toast("you touch ${(body.userData as? BodyItem)?.id}")
                    return true
                }
            }
        }
        return super.onTouchEvent(event)

    }


    private fun drawBody(canvas: Canvas, body: Body) {
        val bodyItem = (body.userData as? BodyItem) ?: return
        canvas.rotate(body.angle)
        if (bodyItem.shape == PolygonShape) {
            canvas.drawRect(
                body.position.x.meterToPx,
                body.position.y.meterToPx,
                body.position.x.meterToPx + bodyItem.width,
                body.position.y.meterToPx + bodyItem.height,
                rectPainter
            )
        } else if (bodyItem.shape == CircleShape) {
            val radius = bodyItem.width / 2
            val cx = body.position.x + radius
            val cy = body.position.y + radius
            canvas.drawCircle(cx, cy, radius, circlePainter)
        }
        canvas.rotate(-body.angle)

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        rainScope.cancel()
    }

    suspend fun play(param: WorldParam, list: List<BodyItem>) {
        bodyList.clear()
        world.clearForces()
        world.dispose()

        worldParam = param
        world = createWorld(param)
        bodyList.addAll(list.map { createBody(world, it) })
        innerPlay(param.playTime)
    }

    private suspend fun innerPlay(playTime: Int) = withContext(Dispatchers.IO) {
        val param = worldParam ?: return@withContext
        var currentTime = 0
        while (currentTime < playTime) {
            currentTime = playTime - (param.timeStep * 1000).toInt()
            world.step(param.timeStep, param.velocityIterations, param.positionIterations)
            val canvas = holder?.lockCanvas(Rect(0, 0, 1080, 1000)) ?: return@withContext
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            bodyList.forEach {
                drawBody(canvas, it)
            }
            holder?.unlockCanvasAndPost(canvas)
            delay((param.timeStep * 1000).toLong())
        }

    }



}