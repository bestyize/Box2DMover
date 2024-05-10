package com.thewind.box2dmover.effects.module.page.beziereditor.model

data class BezierEditorPageState(
    val openCopyDialog: Boolean = false,
    val openOptionDialog: Boolean = false,
    val element: BezierAnimateElement = BezierAnimateElement(),
    val animatorJson: String = ""
)
