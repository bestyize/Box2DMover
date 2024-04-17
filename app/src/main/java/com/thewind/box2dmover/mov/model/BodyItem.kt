package com.thewind.box2dmover.mov.model

import android.view.View
import com.badlogic.gdx.physics.box2d.Shape
import com.thewind.box2dmover.mov.util.pxToMeter

data class BodyItem(
    val id: Int = 0,
    val width: Float = 0f,
    val height: Float = 0f,
    val startX: Int = 0,
    val startY: Int = 0,
    val shape: Int = PolygonShape,
    val angle: Float = 0f,
    val physicalParam: PhysicalParam = PhysicalParam(),
    val desc: String = ""
) {
    var view: View? = null
}


val BodyItem.box2DShape: Shape
    get() = when (shape) {
        PolygonShape -> com.badlogic.gdx.physics.box2d.PolygonShape().apply {
            setAsBox(width.pxToMeter, height.pxToMeter)
        }

        CircleShape -> com.badlogic.gdx.physics.box2d.CircleShape().apply {
            radius = width.pxToMeter
        }

        else -> com.badlogic.gdx.physics.box2d.PolygonShape()
    }
