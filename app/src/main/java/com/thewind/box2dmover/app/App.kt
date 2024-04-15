package com.thewind.box2dmover.app

import android.app.Application
import android.widget.Toast
import com.badlogic.gdx.physics.box2d.Box2D

class App : Application() {
    override fun onCreate() {
        _app = this
        super.onCreate()
        Box2D.init()
    }

    companion object {
        private lateinit var _app: Application
        val app by lazy { _app }
    }
}

fun toast(msg: String? = null) {
    msg ?: return
    Toast.makeText(App.app, msg, Toast.LENGTH_SHORT).show()
}