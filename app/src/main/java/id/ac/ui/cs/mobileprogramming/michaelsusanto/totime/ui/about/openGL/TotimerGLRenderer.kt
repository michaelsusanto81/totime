package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.about.openGL

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.sin

class TotimerGLRenderer: GLSurfaceView.Renderer {

    private var blueValue = 1.0f
    private val FLASH_DURATION = 5000.0

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, blueValue, 1.0f)
    }

    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES20.glClearColor(0.0f, 0.0f, blueValue, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        blueValue = (((sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION) * 0.5) + 0.5).toFloat());
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

}