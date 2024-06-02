package com.thewind.box2dmover.effects.module.page.beziereditor.preview.elementview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

open class BezierBaseElementView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, attrDef: Int = 0, attrStyle: Int = 0
) : FrameLayout(context, attributeSet, attrDef, attrStyle) {

    @Suppress("unused")
    fun setCenterX(tx: Float) {
        x = tx - width / 2
    }

    @Suppress("unused")
    fun getCenterX(): Float {
        return x + width / 2
    }

    @Suppress("unused")
    fun setCenterY(ty: Float) {
        y = ty - height / 2
    }

    @Suppress("unused")
    fun getCenterY(): Float {
        return y + height / 2
    }

    companion object {
        const val ANIM_CENTER_X = "centerX"
        const val ANIM_CENTER_Y = "centerY"

        const val ANIM_ROTATE_Z = "rotation"
        const val ANIM_ALPHA = "alpha"

        const val ANIM_SCALE_X = "scaleX"

        const val ANIM_SCALE_Y = "scaleY"

        const val ANIM_SIZE = "size"
    }
}