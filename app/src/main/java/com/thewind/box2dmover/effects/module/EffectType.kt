package com.thewind.box2dmover.effects.module

enum class EffectType(val title: String, val router: String, val support: Boolean) {
    BalloonFly("气球上升", "/page/balloonFly", true),
    SnowDrop("雪花飘落", "/page/snowDrop", true),
    BalloonFlyBinding("气球绑定货物", "/page/balloonFlyBinding", false),
    BezierEditor("贝塞尔参数编辑", "/page/bezierEditor", true),
    BezierPreview("贝塞尔动画预览", "/page/bezierPreview", true)
}