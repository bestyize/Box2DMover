package com.thewind.box2dmover.effects.module.datacenter

import com.thewind.box2dmover.mov.factory.createBox2DFullScreenBoundBound
import com.thewind.box2dmover.mov.model.BodyItem
import com.thewind.box2dmover.mov.model.DynamicBody
import com.thewind.box2dmover.mov.model.PhysicalParam
import kotlin.random.Random

internal fun createRandomUpSnow(): List<BodyItem> = createBox2DFullScreenBoundBound(bottom = false) + createBody()


private fun createBody(): List<BodyItem> {
    val random = Random(System.currentTimeMillis())
    val list = mutableListOf<BodyItem>()
    repeat(20) {
        val size = random.nextInt(90, 150)
        list.add(
            BodyItem(
                id = it,
                startX = random.nextInt(50, 1030),
                startY = random.nextInt(2400, 3800),
                width = size.toFloat(),
                height = size.toFloat(),
                angle = random.nextInt(0, 90).toFloat(),
                physicalParam = PhysicalParam(
                    bodyType = DynamicBody,
                    linearVelocityY = -(random.nextFloat() * 2).coerceAtLeast(0.8f).coerceAtMost(2f),
                    gravityScale = -random.nextFloat().coerceAtMost(1f).coerceAtLeast(0.3f)
                )
            )
        )
    }

    return list
}