package com.thewind.box2dmover.rain2.effect

import com.thewind.box2dmover.mov.factory.createBox2DFullScreenBoundBound
import com.thewind.box2dmover.mov.model.BodyItem
import com.thewind.box2dmover.mov.model.DynamicBody
import com.thewind.box2dmover.mov.model.PhysicalParam
import kotlin.random.Random

internal fun createRandomDownSnow(): List<BodyItem> =
    createBox2DFullScreenBoundBound(top = false, bottom = true) + randomBody()

private fun randomBody(): List<BodyItem> {
    val random = Random(System.currentTimeMillis())
    val list = mutableListOf<BodyItem>()
    repeat(36) {
        val size = random.nextInt(40, 120)
        list.add(
            BodyItem(
                id = it,
                startX = random.nextInt(50, 1030),
                startY = random.nextInt(-3600, -50),
                width = size.toFloat(),
                height = size.toFloat(),
                angle = random.nextInt(0, 90).toFloat(),
                physicalParam = PhysicalParam(
                    bodyType = DynamicBody,
                    linearVelocityY = (random.nextFloat() * 2).coerceAtLeast(0.8f).coerceAtMost(3f),
                    gravityScale = random.nextFloat().coerceAtMost(1f).coerceAtLeast(0.3f)
                )
            )
        )
    }
    return list
}
