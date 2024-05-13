package com.thewind.box2dmover.effects.module.page.beziereditor.model

enum class BezierEditorPageAction(val title: String) {
    AddElement("添加新元素"), RemoveElement("删除当前元素"), Add("添加动画"), Export("导出动画"), Preview(
        "预览动画"
    ),
    EditPreview("编辑预览画布大小"), SaveAnimation("保存动画参数")
}