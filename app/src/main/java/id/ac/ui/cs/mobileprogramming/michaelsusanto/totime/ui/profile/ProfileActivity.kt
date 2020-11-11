package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        setupToolbar()
        binding.btnEdit.setOnClickListener { edit() }
        binding.btnSave.setOnClickListener { save() }
        binding.btnCancel.setOnClickListener { cancel() }
        binding.btnCamera.setOnClickListener { camera() }
        binding.btnGallery.setOnClickListener { gallery() }
    }

    private fun edit() {
        binding.nameLabel.visibility = View.GONE
        binding.emailLabel.visibility = View.GONE
        binding.btnEdit.visibility = View.GONE
        binding.nameField.visibility = View.VISIBLE
        binding.emailField.visibility = View.VISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.btnCancel.visibility = View.VISIBLE
        binding.btnCamera.visibility = View.VISIBLE
        binding.btnGallery.visibility = View.VISIBLE
    }

    private fun save() {
        cancel()
    }

    private fun cancel() {
        binding.nameLabel.visibility = View.VISIBLE
        binding.emailLabel.visibility = View.VISIBLE
        binding.btnEdit.visibility = View.VISIBLE
        binding.nameField.visibility = View.GONE
        binding.emailField.visibility = View.GONE
        binding.btnSave.visibility = View.GONE
        binding.btnCancel.visibility = View.GONE
        binding.btnCamera.visibility = View.GONE
        binding.btnGallery.visibility = View.GONE
    }

    private fun camera() {

    }

    private fun gallery() {

    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.profile)
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