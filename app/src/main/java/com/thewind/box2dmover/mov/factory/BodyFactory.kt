package com.thewind.box2dmover.mov.factory

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World
import com.thewind.box2dmover.mov.model.BodyItem
import com.thewind.box2dmover.mov.model.box2DShape
import com.thewind.box2dmover.mov.model.box2dBodyType
import com.thewind.box2dmover.mov.util.pxToMeter

fun createBody(world: World, bodyItem: BodyItem): Body {

    val bodyDef = BodyDef().apply {
        type = bodyItem.physicalParam.bodyType.box2dBodyType
        position.x = bodyItem.startX.pxToMeter
        position.y = bodyItem.startY.pxToMeter
        fixedRotation = bodyItem.fixedRotation
        linearDamping = bodyItem.physicalParam.linearDamping
        gravityScale = bodyItem.physicalParam.gravityScale
        linearVelocity.set(
            bodyItem.physicalParam.linearVelocityX,
            bodyItem.physicalParam.linearVelocityY
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