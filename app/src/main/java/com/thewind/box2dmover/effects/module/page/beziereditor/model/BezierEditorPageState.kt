package com.thewind.box2dmover.effects.module.page.beziereditor.model

data class BezierEditorPageState(
    val openCopyDialog: Boolean = false,
    val openOptionDialog: Boolean = false,
    val openContainerEditorPage: Boolean = false,
    val previewContainerWidth: Int = 350,
    val previewContainerHeight: Int = 700,
    val elements: List<BezierAnimateElement> = listOf(
        BezierAnimateElement(), BezierAnimateElement()
    ),
    val animatorJson: String = ""
)
