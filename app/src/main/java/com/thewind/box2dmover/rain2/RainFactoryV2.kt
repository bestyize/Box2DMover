package com.thewind.box2dmover.rain2

import com.thewind.box2dmover.mov.factory.createBox2DFullScreenBoundBound
import com.thewind.box2dmover.mov.model.BodyItem
import com.thewind.box2dmover.mov.model.CircleShape
import com.thewind.box2dmover.mov.model.DynamicBody
import com.thewind.box2dmover.mov.model.PhysicalParam
import com.thewind.box2dmover.mov.model.PolygonShape

fun createRainV2(): List<BodyItem> {
    return createBody() + createBox2DFullScreenBoundBound(top = false)
}


private fun createBody(): List<BodyItem> = listOf(
    BodyItem(
        id = 0,
        width = 60f,
        height = 60f,
        startX = 100,
        startY = -100,
        shape = CircleShape,
        angle = 0f,
        physicalParam = PhysicalParam(
            bodyType = DynamicBody,
            gravityScale = 0.5f,
            restitution = 0.8f,
            friction = 1f,
            linearVelocityX = -1f,
            fixedRotation = true
        )
    ), BodyItem(
        id = 1,
        width = 60f,
        height = 60f,
        startX = 200,
        startY = -100,
        shape = CircleShape,
        angle = 0f,
        physicalParam = PhysicalParam(
            bodyType = DynamicBody, friction = 1f, gravityScale = 0.3f
        )
    ), BodyItem(
        id = 2,
        width = 100f,
        height = 100f,
        startX = 300,
        startY = -100,
        shape = CircleShape,
        angle = 0f,
        physicalParam = PhysicalParam(
            bodyType = DynamicBody, friction = 1f, gravityScale = 0.6f
        )
    ), BodyItem(
        id = 3,
        width = 120f,
        height = 120f,
        startX = 350,
        startY = -100,
        shape = PolygonShape,
        angle = 0f,
        physicalParam = PhysicalParam(
            bodyType = DynamicBody, friction = 1f, gravityScale = 0.7f
        )
    ), BodyItem(
        id = 4,
        width = 60f,
        height = 60f,
        startX = 500,
        startY = -200,
        shape = PolygonShape,
        angle = 0f,
        physicalParam = PhysicalParam(
            bodyType = DynamicBody, friction = 1f
        )
    ), BodyItem(
        id = 5,
        width = 150f,
        height = 150f,
        startX = 700,
        startY = -200,
        shape = PolygonShape,
        angle = 0f,
        physicalParam = PhysicalParam(
            bodyType = DynamicBody,
            friction = 1f,
            gravityScale = 0.4f,
            linearVelocityX = -2f,

            )
    ), BodyItem(
        id = 6,
        width = 50f,
        height = 50f,
        startX = 750,
        startY = -50,
        shape = PolygonShape,
        angle = 0f,
        physicalParam = PhysicalParam(
            bodyType = DynamicBody, friction = 1f, gravityScale = 0.4f
        )
    ), BodyItem(
        id = 7,
        width = 150f,
        height = 150f,
        startX = 850,
        startY = -100,
        shape = PolygonShape,
        angle = 0f,
        physicalParam = PhysicalParam(
            bodyType = DynamicBody, friction = 1f, gravityScale = 0.3f
        )
    )
)

