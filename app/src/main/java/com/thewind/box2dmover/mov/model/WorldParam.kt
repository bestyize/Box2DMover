package com.thewind.box2dmover.mov.model

data class WorldParam(
    val xGravity: Float = 0f, // 水平方向的重力加速度，可正可负
    val yGravity: Float = 10f, //垂直方向的重力加速度， 可正可负
    val doSleep: Boolean = true, // 允许world休眠，用于提高性能
    val timeStep: Float = 1 / 60f, // 步频，刷新率
    val velocityIterations: Int = 6, // 速度迭代次数
    val positionIterations: Int = 2, // 位置迭代次数
    val playTime: Int = 10000,
)