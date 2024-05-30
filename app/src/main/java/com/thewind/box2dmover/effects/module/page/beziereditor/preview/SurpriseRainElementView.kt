package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import android.content.Context
import android.util.AttributeSet
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement

class SurpriseRainElementView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, attrDef: Int = 0, attrStyle: Int = 0
) : BezierBaseElementView(context, attributeSet, attrDef, attrStyle) {

    var clickListener: (() -> Unit)? = null

    fun bindData(
        element: BezierAnimateElement, containerWidth: Float, containerHeight: Float
    ) {
        setBackgroundColor(0x7FFF0000)
    }


}