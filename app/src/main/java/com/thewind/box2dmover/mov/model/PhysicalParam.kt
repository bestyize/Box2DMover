package com.thewind.box2dmover.mov.model

import com.badlogic.gdx.physics.box2d.BodyDef

//https://www.jianshu.com/p/4895c998d511
data class PhysicalParam(
    val bodyType: Int = DynamicBody,
    val gravityScale: Float = 1f,
    val fixedRotation: Boolean = false,
    val density: Float = 1f, // 物体密度
    val friction: Float = 0f, // 摩擦力， 0表示绝对光滑 1表示绝对不光滑
    val restitution: Float = 0.5f, // 弹力 0表示不反弹， 1表示最多可以反弹到起始高度， 大于1表示回弹会更高。
    val linearDamping: Float = 0f,// 减缓线性速度
    val isSensor: Boolean = false,// 如为true，则该物体可以与其他物体碰撞， 否则只发生位置叠加，而无碰撞效果
    val linearVelocityX: Float = 0f, // speed x
    val linearVelocityY: Float = 0f, // speed y
)

val Int.box2dBodyType: BodyDef.BodyType
    get() = when (this) {
        DynamicBody -> BodyDef.BodyType.DynamicBody
        StaticBody -> BodyDef.BodyType.StaticBody
        KinematicBody -> BodyDef.BodyType.KinematicBody
        else -> BodyDef.BodyType.DynamicBody
    }




