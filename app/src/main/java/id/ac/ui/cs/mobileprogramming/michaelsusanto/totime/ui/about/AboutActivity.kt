package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.about

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.ActivityAboutBinding
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.about.openGL.TotimerGLSurfaceView

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding
    private lateinit var gLView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gLView = TotimerGLSurfaceView(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)
        setupToolbar()
    }

    override fun onPause() {
        super.onPause()
        binding.glView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.glView.onResume()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> pressBack()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun pressBack(): Boolean {
        onBackPressed()
        return true
    }
}