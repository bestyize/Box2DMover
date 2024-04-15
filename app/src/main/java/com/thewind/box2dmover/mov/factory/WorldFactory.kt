package com.thewind.box2dmover.mov.factory

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.thewind.box2dmover.mov.model.WorldParam

fun createWorld(param: WorldParam): World {
    return World(Vector2(param.xGravity, param.yGravity), param.doSleep)
}