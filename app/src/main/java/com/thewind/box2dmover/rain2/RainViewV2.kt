package com.thewind.box2dmover.rain2

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.thewind.box2dmover.R
import com.thewind.box2dmover.app.toast
import com.thewind.box2dmover.mov.factory.createBody
import com.thewind.box2dmover.mov.factory.createWorld
import com.thewind.box2dmover.mov.model.BodyItem
import com.thewind.box2dmover.mov.model.WorldParam
import com.thewind.box2dmover.mov.util.b2dAngleToViewAngle
import com.thewind.box2dmover.mov.util.detectTouchBody
import com.thewind.box2dmover.mov.util.meterToPx
import com.thewind.box2dmover.mov.util.viewAngleToB2dAngle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RainViewV2(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {


    private val drawScope = CoroutineScope(Dispatchers.Main)

    private var job: Job? = null


    private var worldParam: WorldParam? = null

    private var world: World = createWorld(WorldParam(yGravity = 10f))

    private val bodyList: MutableList<Body> = mutableListOf()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val ev = event ?: return super.onTouchEvent(null)
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            val body = bodyList.find { it.detectTouchBody(event) }
            if (body != null) {
                toast("you touch ${(body.userData as? BodyItem)?.id}")
                return true
            }
        }
        return super.onTouchEvent(event)
    }


    fun play(param: WorldParam, list: List<BodyItem>) {
        if (job?.isActive == true) {
            job?.cancel()
        }
        rebuildWorld(param, list)
        job = drawScope.launch {
            innerPlay(param.playTime)
        }
    }

    private fun rebuildWorld(param: WorldParam, list: List<BodyItem>) {
        bodyList.forEach {
            val view = (it.userData as? BodyItem)?.view
            (view?.parent as? ViewGroup)?.removeView(view)
            world.destroyBody(it)
        }
        bodyList.clear()
        world.clearForces()
        world.dispose()
        worldParam = param
        world = createWorld(param)
        bodyList.addAll(list.map { createBody(world, it.bindView()) })
    }

    private suspend fun innerPlay(playTime: Int) {
        val param = worldParam ?: return
        var currentTime = 0
        while (currentTime < playTime) {
            currentTime = playTime - (param.timeStep * 1000).toInt()
            world.step(param.timeStep, param.velocityIterations, param.positionIterations)
            bodyList.forEach {
                updatePosition(it)
            }
            delay((param.timeStep * 1000).toLong())
        }

    }

    private fun updatePosition(body: Body) {
        val bodyItem = body.userData as? BodyItem ?: return
        bodyItem.view?.apply {
            x = body.position.x.meterToPx
            y = body.position.y.meterToPx
            rotation = body.angle.b2dAngleToViewAngle
        }
    }


    private fun BodyItem.bindView(): BodyItem {
        val bodyItem = this
        val imageView = ImageView(context).apply {
            x = bodyItem.startX.toFloat()
            y = bodyItem.startY.toFloat()
            layoutParams = ViewGroup.LayoutParams(bodyItem.width.toInt(), bodyItem.height.toInt())
            rotation = bodyItem.angle.viewAngleToB2dAngle
            scaleType = ImageView.ScaleType.FIT_XY
            setImageResource(R.drawable.chengzi)
        }
        bodyItem.view = imageView
        addView(imageView)
        return this
    }

}