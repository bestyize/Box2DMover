package com.thewind.box2dmover.effects.module

enum class EffectType(val title: String, val router: String, val support: Boolean) {
    BalloonFly("气球上升", "/page/balloonFly", true),
    SnowDrop("雪花飘落", "/page/snowDrop", true),
    BalloonFlyBinding("气球绑定货物", "/page/balloonFlyBinding", false),
    BezierPath("贝塞尔曲线", "/page/bezierPath", true),
    BezierAxios("贝塞尔坐标", "/page/bezierAxios", true)
}