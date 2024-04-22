package com.thewind.box2dmover.mov.factory

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World
import com.thewind.box2dmover.mov.model.BodyItem
import com.thewind.box2dmover.mov.model.PhysicalParam
import com.thewind.box2dmover.mov.model.StaticBody
import com.thewind.box2dmover.mov.model.box2DShape
import com.thewind.box2dmover.mov.model.box2dBodyType
import com.thewind.box2dmover.mov.util.pxToMeter
import com.thewind.box2dmover.mov.util.screenHeight
import com.thewind.box2dmover.mov.util.screenWidth

fun createBody(world: World, bodyItem: BodyItem): Body {

    val bodyDef = BodyDef().apply {
        type = bodyItem.physicalParam.bodyType.box2dBodyType
        position.x = bodyItem.startX.pxToMeter
        position.y = bodyItem.startY.pxToMeter
        fixedRotation = bodyItem.physicalParam.fixedRotation
        linearDamping = bodyItem.physicalParam.linearDamping
        gravityScale = bodyItem.physicalParam.gravityScale
        linearVelocity.set(
            bodyItem.physicalParam.linearVelocityX, bodyItem.physicalParam.linearVelocityY
        )

    }

    val body = world.createBody(bodyDef).apply {
        userData = bodyItem
    }

    val b2Shape = bodyItem.box2DShape
    val fixtureDef = FixtureDef().apply {
        shape = b2Shape
        density = bodyItem.physicalParam.density
        friction = bodyItem.physicalParam.friction
        restitution = bodyItem.physicalParam.restitution
        isSensor = bodyItem.physicalParam.isSensor
    }
    body.createFixture(fixtureDef)
    b2Shape.dispose()
    return body
}

fun createBox2DFullScreenBoundBound(
    left: Boolean = true, top: Boolean = true, right: Boolean = true, bottom: Boolean = true
): List<BodyItem> {

    return mutableListOf<BodyItem>().apply {
        if (left) {
            add(
                BodyItem(
                    desc = "left bound",
                    id = 10000,
                    width = 0f,
                    height = screenHeight.toFloat() * 2,
                    startX = 0,
                    startY = 0,
                    physicalParam = PhysicalParam(bodyType = StaticBody, friction = 1f)
                )
            )
        }
        if (top) {
            add(
                BodyItem(
                    desc = "top bound",
                    id = 10001,
                    width = screenWidth.toFloat() * 2,
                    height = 0f,
                    startX = 0,
                    startY = -50,
                    physicalParam = PhysicalParam(bodyType = StaticBody, friction = 1f)
                )
            )
        }

        if (right) {
            add(
                BodyItem(
                    desc = "right bound",
                    id = 10002,
                    width = 0f,
                    height = screenHeight.toFloat() * 2,
                    startX = screenWidth,
                    startY = 0,
                    physicalParam = PhysicalParam(bodyType = StaticBody, friction = 1f)
                )
            )
        }

        if (bottom) {
            add(
                BodyItem(
                    desc = "bottom bound",
                    id = 10003,
                    width = screenWidth.toFloat() * 2,
                    height = 0f,
                    startX = 0,
                    startY = (screenHeight * 1).toInt(),
                    physicalParam = PhysicalParam(bodyType = StaticBody, friction = 1f)
                )
            )
        }
    }
}