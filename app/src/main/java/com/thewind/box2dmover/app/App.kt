package com.thewind.box2dmover.app

import android.app.Application
import android.widget.Toast
import com.badlogic.gdx.physics.box2d.Box2D
import com.tencent.mmkv.MMKV

class App : Application() {
    override fun onCreate() {
        _app = this
        super.onCreate()
        MMKV.initialize(this)
        Box2D.init()
    }

    companion object {
        private lateinit var _app: Application
        val app by lazy { _app }

        var heightPx: Int = 0
        var widthPx: Int = 0
    }
}

fun toast(msg: String? = null) {
    msg ?: return
    Toast.makeText(App.app, msg, Toast.LENGTH_SHORT).show()
}