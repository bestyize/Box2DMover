package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import android.graphics.PointF
import android.view.animation.Interpolator

class BezierInterpolator(x1: Float, y1: Float, x2: Float, y2: Float) : Interpolator {
    private var mLastI = 0
    private val mControlPoint1 = PointF()
    private val mControlPoint2 = PointF()

    /**
     * 设置中间两个控制点.<br></br>
     * 在线工具: http://cubic-bezier.com/<br></br>
     *
     * @param x1 p1 x坐标
     * @param y1 p1 y坐标
     * @param x2 p2 x坐标
     * @param y2 p2 y坐标
     */
    init {
        mControlPoint1.x = x1
        mControlPoint1.y = y1
        mControlPoint2.x = x2
        mControlPoint2.y = y2
    }

    override fun getInterpolation(input: Float): Float {
        var t = input
        // 近似求解t的值[0,1]
        for (i in mLastI until ACCURACY) {
            t = 1.0f * i / ACCURACY
            val x = cubicCurves(
                t.toDouble(),
                0.0,
                mControlPoint1.x.toDouble(),
                mControlPoint2.x.toDouble(),
                1.0
            )
            if (x >= input) {
                mLastI = i
                break
            }
        }
        var value = cubicCurves(
            t.toDouble(),
            0.0,
            mControlPoint1.y.toDouble(),
            mControlPoint2.y.toDouble(),
            1.0
        )
        if (value > 0.999) {
            value = 1.0
            mLastI = 0
        }
        return value.toFloat()
    }

}

private const val ACCURACY = 4096

/**
 * 求三次贝塞尔曲线(四个控制点)一个点某个维度的值.<br></br>
 * 参考资料: * http://devmag.org.za/2011/04/05/bzier-curves-a-tutorial/ *
 *
 * @param t      取值[0, 1]
 * @param value0 p1 x
 * @param value1 p1 y
 * @param value2 p2 x
 * @param value3 p2 y
 * @return progress
 */
fun cubicCurves(
    t: Double, value0: Double, value1: Double,
    value2: Double, value3: Double
): Double {
    val u = 1 - t
    val tt = t * t
    val uu = u * u
    val uuu = uu * u
    val ttt = tt * t
    var value = uuu * value0
    value += 3 * uu * t * value1
    value += 3 * u * tt * value2
    value += ttt * value3
    return value
}