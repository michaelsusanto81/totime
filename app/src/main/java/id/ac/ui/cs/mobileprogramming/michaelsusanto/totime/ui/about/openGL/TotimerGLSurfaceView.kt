package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.about.openGL

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

class TotimerGLSurfaceView: GLSurfaceView {

    private lateinit var renderer: TotimerGLRenderer

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = TotimerGLRenderer()

        // store opengl context
        preserveEGLContextOnPause = true

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }
}