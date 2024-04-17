package com.thewind.box2dmover.mov.util

import android.view.MotionEvent
import com.badlogic.gdx.physics.box2d.Body
import com.thewind.box2dmover.mov.model.BodyItem

fun Body.detectTouchBody(motionEvent: MotionEvent): Boolean {
    val data = (userData as? BodyItem) ?: return false
    val startX = position.x.meterToPx
    val startY = position.y.meterToPx
    return motionEvent.x >= startX && motionEvent.x <= startX + data.width && motionEvent.y >= startY && motionEvent.y <= startY + data.height
}