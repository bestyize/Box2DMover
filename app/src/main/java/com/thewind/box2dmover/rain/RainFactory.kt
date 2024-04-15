package com.thewind.box2dmover.rain

import com.thewind.box2dmover.mov.model.BodyItem
import com.thewind.box2dmover.mov.model.CircleShape
import com.thewind.box2dmover.mov.model.DynamicBody
import com.thewind.box2dmover.mov.model.PhysicalParam
import com.thewind.box2dmover.mov.model.PolygonShape
import com.thewind.box2dmover.mov.model.StaticBody

fun createRain(): List<BodyItem> {
    return listOf(
        BodyItem(
            id = 0,
            width = 60f,
            height = 60f,
            startX = 100,
            startY = 100,
            shape = PolygonShape,
            angle = 0f,
            fixedRotation = true,
            physicalParam = PhysicalParam(
                bodyType = DynamicBody,
                gravityScale = 10f,
                restitution = 2f
            )
        ),
        BodyItem(
            id = 1,
            width = 60f,
            height = 60f,
            startX = 200,
            startY = 100,
            shape = CircleShape,
            angle = 0f,
            physicalParam = PhysicalParam(
                bodyType = DynamicBody,
                gravityScale = 0.8f,
                density = 1f,
                restitution = 0f
            )
        ),
        BodyItem(
            id = 2,
            width = 40f,
            height = 60f,
            startX = 300,
            startY = 100,
            shape = PolygonShape,
            angle = 0f,
            physicalParam = PhysicalParam(
                bodyType = DynamicBody,
                gravityScale = 1f,
                restitution = 0f
            )
        ),
        BodyItem(
            id = 3,
            width = 60f,
            height = 60f,
            startX = 350,
            startY = -100,
            shape = PolygonShape,
            angle = 0f,
            physicalParam = PhysicalParam(
                bodyType = DynamicBody,
                gravityScale = 1f,
                restitution = 0f
            )
        ),
        BodyItem(
            id = 4,
            width = 60f,
            height = 60f,
            startX = 500,
            startY = 200,
            shape = PolygonShape,
            angle = 0f,
            physicalParam = PhysicalParam(
                bodyType = DynamicBody,
                gravityScale = 1f,
                restitution = 0f,
                linearVelocityX = 5f
            )
        ),
        BodyItem(
            id = 5,
            width = 150f,
            height = 150f,
            startX = 700,
            startY = 200,
            shape = PolygonShape,
            angle = 0f,
            physicalParam = PhysicalParam(
                bodyType = DynamicBody,
                gravityScale = 1f,
                restitution = 0f,
                linearVelocityX = 5f
            )
        ),
        BodyItem(
            id = 6,
            width = 2200f,
            height = 50f,
            startX = 0,
            startY = 2100,
            shape = PolygonShape,
            angle = 0f,
            physicalParam = PhysicalParam(
                bodyType = StaticBody,
                gravityScale = 1f,
                density = 10000f,
                friction = 10f,
                restitution = 0f
            )
        )
    )
}