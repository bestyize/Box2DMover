package com.thewind.box2dmover.effects.module

enum class EffectType(val title: String, val router: String, val support: Boolean) {
    ComposeBezierPreview("元素动画预览(Compose)", "/page/bezierPreviewCompose", true),
    SnowDrop("雪花飘落", "/page/snowDrop", false),
    BalloonFlyBinding("气球绑定货物", "/page/balloonFlyBinding", false),
    BezierEditor("元素动画参数编辑", "/page/bezierEditor", true),
    BezierPreview("元素动画预览", "/page/bezierPreview", true)
}